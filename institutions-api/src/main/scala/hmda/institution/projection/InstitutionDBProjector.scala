package hmda.institution.projection

import akka.actor.{ActorSystem, Scheduler}
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.AskPattern._
import hmda.query.HmdaQuery._
import akka.actor.typed.scaladsl.adapter._
import akka.persistence.query.{EventEnvelope, NoOffset, Offset, Sequence}
import akka.persistence.typed.scaladsl.{Effect, PersistentBehaviors}
import akka.persistence.typed.scaladsl.PersistentBehaviors.CommandHandler
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import hmda.institution.api.http.InstitutionConverter
import hmda.institution.query.InstitutionComponent
import hmda.messages.institution.InstitutionEvents.{
  InstitutionCreated,
  InstitutionDeleted,
  InstitutionModified
}
import hmda.messages.projection.CommonProjectionMessages._
import hmda.query.DbConfiguration._

import scala.concurrent.Future
import scala.concurrent.duration._

object InstitutionDBProjector extends InstitutionComponent {

  final val name = "InstitutionDBProjector"

  implicit val institutionRepository = new InstitutionRepository(dbConfig)
  implicit val institutionEmailsRepository = new InstitutionEmailsRepository(
    dbConfig)

  case class InstitutionDBProjectorState(offset: Offset = NoOffset)

  val config = ConfigFactory.load()
  val duration = config.getInt("hmda.institution.timeout")

  implicit val timeout = Timeout(duration.seconds)

  def behavior: Behavior[ProjectionCommand] =
    PersistentBehaviors
      .receive[ProjectionCommand, ProjectionEvent, InstitutionDBProjectorState](
        persistenceId = name,
        emptyState = InstitutionDBProjectorState(),
        commandHandler = commandHandler,
        eventHandler = eventHandler
      )

  val commandHandler: CommandHandler[ProjectionCommand,
                                     ProjectionEvent,
                                     InstitutionDBProjectorState] = {
    (ctx, state, cmd) =>
      cmd match {
        case StartStreaming =>
          implicit val system: ActorSystem = ctx.system.toUntyped
          implicit val materializer: ActorMaterializer = ActorMaterializer()
          implicit val scheduler: Scheduler = ctx.system.scheduler
          ctx.log.info("Streaming messages from {}", name)
          readJournal(system)
            .eventsByTag("institution", state.offset)
            .map { env =>
              ctx.log.info(env.toString)
              projectEvent(env)
            }
            .map { env =>
              val actorRef = ctx.self
              val result: Future[OffsetSaved] = actorRef ? (ref =>
                SaveOffset(env.offset, ref))
              result
            }
            .runWith(Sink.ignore)
          Effect.none

        case SaveOffset(offset, replyTo) =>
          Effect.persist(OffsetSaved(offset)).andThen {
            ctx.log.info("Offset saved: {}", offset)
            replyTo ! OffsetSaved(offset)
          }

        case GetOffset(replyTo) =>
          replyTo ! OffsetSaved(state.offset)
          Effect.none

      }
  }

  val eventHandler: (InstitutionDBProjectorState,
                     ProjectionEvent) => InstitutionDBProjectorState = {
    case (state, OffsetSaved(offset)) => state.copy(offset = offset)
  }

  private def projectEvent(envelope: EventEnvelope): EventEnvelope = {
    val event = envelope.event
    event match {
      case InstitutionCreated(i) =>
        institutionRepository.insertOrUpdate(InstitutionConverter.convert(i))
        val emails = InstitutionConverter.emailsFromInstitution(i)
        emails.foreach(email =>
          institutionEmailsRepository.insertOrUpdate(email))

      case InstitutionModified(i) =>
        institutionRepository.insertOrUpdate(InstitutionConverter.convert(i))
      case InstitutionDeleted(lei) =>
        institutionRepository.deleteById(lei)
    }
    envelope
  }

}
