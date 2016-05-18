import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.collection.mutable
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.math._
import spray.json.{JsonParser, DefaultJsonProtocol}

// TODO https://github.com/tastejs/todomvc/tree/gh-pages/examples/react-backbone

case class IpInfo(ip: String, country: Option[String], city: Option[String], latitude: Option[Double], longitude: Option[Double])

case class IpPairSummaryRequest(ip1: String, ip2: String)

case class IpPairSummary(distance: Option[Double], ip1Info: IpInfo, ip2Info: IpInfo)


case class JustInfo(ip: String, country: Option[String])

case class JustRequest(title: String, order: Int, completed: Boolean)

trait Protocols extends DefaultJsonProtocol {
  implicit val justInfoFormat = jsonFormat2(JustInfo.apply)
  implicit val justRequestFormat = jsonFormat3(JustRequest.apply)

  implicit val ipInfoFormat = jsonFormat5(IpInfo.apply)
  implicit val ipPairSummaryRequestFormat = jsonFormat2(IpPairSummaryRequest.apply)
  implicit val ipPairSummaryFormat = jsonFormat3(IpPairSummary.apply)
}

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter

//  lazy val projectPath = config.getString("project.path")

  val data = mutable.Map(
     "zz1" -> JustRequest("zz1", 1, completed = false)
    ,"zz2" -> JustRequest("zz2", 2, completed = false)
    ,"zz3" -> JustRequest("zz3", 3, completed = false)
    ,"zz4" -> JustRequest("zz4", 4, completed = false)
    ,"zz5" -> JustRequest("zz5", 5, completed = false)
  )


  lazy val routes = {
    logRequestResult("akka-http-microservice") {
      path("skillen") {
        getFromResource("index.html")
      } ~
      pathPrefix("assets") {
        getFromResourceDirectory("assets")
      } ~
      pathPrefix("bower_components") {
        getFromResourceDirectory("bower_components")
      } ~
      pathPrefix("js") {
        getFromResourceDirectory("js")
      } ~
      pathPrefix("css") {
        getFromResourceDirectory("css")
      } ~
      pathPrefix("node_modules") {
        getFromResourceDirectory("node_modules")
      } ~
      // REST request from backbone
      pathPrefix("workbench") {
        get {
          complete {
            data.values
          }
        } ~
          (post & entity(as[JustRequest])) { x =>
          complete {
            logger.warning("DEBUG = " + x)
            data(x.title) = x
            JsonParser("""{"success": true}""".stripMargin)
          }
        }

      }
    }
  }
}

object AkkaHttpMicroservice extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

//  println("projectPath = " + projectPath)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}
