import POJO.{JustRequest, WorkbenchInfo}
import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.stream.Materializer
import com.typesafe.config.Config
import controllers.ReadTextValue
import spray.json.JsonParser

import scala.collection.mutable
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config


trait Service extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  def config: Config
  val logger: LoggingAdapter


  //  lazy val projectPath = config.getString("project.path")

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