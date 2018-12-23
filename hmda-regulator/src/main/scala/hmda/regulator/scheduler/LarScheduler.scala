package hmda.regulator.scheduler

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.stream.ActorMaterializer
import akka.stream.alpakka.s3.impl.ListBucketVersion2
import akka.stream.alpakka.s3.javadsl.S3Client
import akka.stream.alpakka.s3.{MemoryBufferType, S3Settings}
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.amazonaws.auth.{AWSStaticCredentialsProvider, BasicAWSCredentials}
import com.amazonaws.regions.AwsRegionProvider
import com.typesafe.akka.extension.quartz.QuartzSchedulerExtension
import com.typesafe.config.ConfigFactory
import hmda.actor.HmdaActor
import hmda.query.DbConfiguration.dbConfig
import hmda.regulator.query.RegulatorComponent
import hmda.regulator.query.lar.LarEntity
import hmda.regulator.scheduler.schedules.Schedules.LarScheduler

import scala.concurrent.Future
import scala.util.{Failure, Success}

class LarScheduler extends HmdaActor with RegulatorComponent {

  implicit val ec = context.system.dispatcher
  implicit val materializer = ActorMaterializer()
  private val fullDate = DateTimeFormatter.ofPattern("yyyy-MM-dd-")
  def larRepository =
    new LarRepository(schema = "public",
                      tableName = "loanapplicationregister2018",
                      dbConfig)

  val awsConfig = ConfigFactory.load("application.conf").getConfig("aws")
  val accessKeyId = awsConfig.getString("access-key-id")
  val secretAccess = awsConfig.getString("secret-access-key ")
  val region = awsConfig.getString("region")
  val bucket = awsConfig.getString("public-bucket")
  val environment = awsConfig.getString("environment")
  val year = awsConfig.getString("year")
  val awsCredentialsProvider = new AWSStaticCredentialsProvider(
    new BasicAWSCredentials(accessKeyId, secretAccess))

  val awsRegionProvider = new AwsRegionProvider {
    override def getRegion: String = region
  }

  val s3Settings = new S3Settings(
    MemoryBufferType,
    None,
    awsCredentialsProvider,
    awsRegionProvider,
    false,
    None,
    ListBucketVersion2
  )

  override def preStart() = {
    QuartzSchedulerExtension(context.system)
      .schedule("LarScheduler", self, LarScheduler)

  }

  override def postStop() = {
    QuartzSchedulerExtension(context.system).cancelJob("LarScheduler")
  }

  override def receive: Receive = {

    case LarScheduler =>
      val s3Client = new S3Client(s3Settings, context.system, materializer)

      val now = LocalDateTime.now()

      val formattedDate = fullDate.format(now)

      val fileName = s"$formattedDate" + s"$year" + "_lar" + ".txt"
      val s3Sink = s3Client.multipartUpload(
        bucket,
        s"$environment/regulator-lar/$year/$fileName")

      val allResults: Future[Seq[LarEntity]] = larRepository.getAllLAR()

      allResults onComplete {
        case Success(lars) => {
          val source = lars
            .map(lar => lar.toPSV + "\n")
            .map(s => ByteString(s))
            .toList

          log.info(
            s"Uploading LAR Regulator Data file : $fileName" + "  to S3.")
          Source(source).runWith(s3Sink)
        }
        case Failure(t) =>
          println(
            "An error has occurred getting LAR Sheet Data: " + t.getMessage)
      }
  }
}
