import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import java.io.{StringReader, IOException}

import controllers.ReadTextValue
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.tokenattributes.{OffsetAttribute, CharTermAttribute}

import scala.collection.mutable
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.math._
import spray.json.{JsonParser, DefaultJsonProtocol}

// TODO https://github.com/tastejs/todomvc/tree/gh-pages/examples/react-backbone


object Main extends App with Service {
  override implicit val system = ActorSystem()
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()

  override val config = ConfigFactory.load()
  override val logger = Logging(system, getClass)

//  println("projectPath = " + projectPath)

  Http().bindAndHandle(routes, config.getString("http.interface"), config.getInt("http.port"))
}


//object Main extends App {
//
//
//}

