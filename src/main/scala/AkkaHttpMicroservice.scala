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


case class JustInfo(ip: String, country: Option[String])

case class JustRequest(t: String, w: String, c: String)

case class WorkbenchInfo(caption: String, text: Seq[Either[String, JustRequest]])


trait Protocols extends DefaultJsonProtocol {
  implicit val justInfoFormat = jsonFormat2(JustInfo.apply)
  implicit val justRequestFormat = jsonFormat3(JustRequest.apply)
  implicit val justWorkbenchInfoFormat = jsonFormat2(WorkbenchInfo.apply)

}

trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter


//  lazy val projectPath = config.getString("project.path")

//  val data = mutable.Map(
//     "zz1" -> JustRequest("zz1", 1, completed = false)
//    ,"zz2" -> JustRequest("zz2", 2, completed = false)
//    ,"zz3" -> JustRequest("zz3", 3, completed = false)
//    ,"zz4" -> JustRequest("zz4", 4, completed = false)
//    ,"zz5" -> JustRequest("zz5", 5, completed = false)
//  )

//  val data = Seq[Either[String, Int]](
//    Left("ololol")
//    ,JustRequest("zz1", 1, completed = false)
//    ,JustRequest("zz2", 2, completed = false)
//      ,"o1231231  lolol"
//    ,JustRequest("zz3", 3, completed = false)
//    ,JustRequest("zz4", 4, completed = false)
//    ,JustRequest("zz5", 5, completed = false)
//  )

  val data: Seq[Either[String, JustRequest]] = Seq(
    JustRequest("zz1", "1", c = "false"),
    "dsadsa 12 1 ",
    JustRequest("zz1", "4", c = "false"),
    " dsadsa"
  ) map {
    case j: JustRequest => Right(j)
    case s: String => Left(s)
  }

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
      pathPrefix("ololo") {
        get {
          complete {
            JsonParser(
              """
                |
                |
                |{"success": true}
                |
                |
                |""".stripMargin)
          }
        }
      } ~
      // REST request from backbone
      pathPrefix("workbench") {
        get {
          complete {
            WorkbenchInfo(
              caption = "dsadsada",
              text = data
            )
          }
        } ~
          (post & entity(as[JustRequest])) { x =>
          complete {
            logger.warning("DEBUG = " + x)
//            data(x.title) = x
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
