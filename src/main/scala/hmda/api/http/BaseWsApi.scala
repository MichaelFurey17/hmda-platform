package hmda.api.http

import java.net.InetAddress
import java.time.Instant

import akka.NotUsed
import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.stream.scaladsl.Flow
import hmda.api.http.model.common.HmdaServiceStatus
import io.circe.generic.auto._
import io.circe.syntax._

trait BaseWsApi {

  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  val log: LoggingAdapter

  def baseHandler(name: String): Flow[Message, Message, NotUsed] = {
    Flow[Message]
      .map {
        case TextMessage.Strict(txt) =>
          txt match {
            case "status" =>
              val now = Instant.now.toString
              val host = InetAddress.getLocalHost.getHostName
              val status = HmdaServiceStatus("OK", name, now, host)
              TextMessage.Strict(status.asJson.toString)

            case _ => TextMessage.Strict("Message not supported")
          }
        case _ => TextMessage.Strict("Message not supported")

      }

  }

  def rootPath(name: String): Route =
    pathSingleSlash {
      get {
        handleWebSocketMessages(baseHandler(name))
      }
    }

  def routes(apiName: String): Route = rootPath(apiName)

}
