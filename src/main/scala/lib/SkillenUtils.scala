package lib

import java.io.{PrintWriter, File}
import akka.actor.ActorSystem
import akka.util.Timeout


import scala.collection.mutable
import scala.concurrent.duration._
import scala.io.Source

import com.redis._

object SkillenUtils {

  val GENRES_DIR = "/Users/oleg/genres/"

  type Vocabulary = mutable.HashMap[String, Double]
  type LangBasis = mutable.HashMap[String, Vocabulary]

  implicit val system = ActorSystem("redis-client")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(5.seconds)

  val redis = RedisClient("localhost", 6379)

  def andTree(dir: File, ext: String): List[File] = {
    val fileList = dir.listFiles.toList
    (fileList filter(f => f.isFile && f.getName.endsWith(ext))) :::
      (fileList filter(f => f.isDirectory) flatMap (f => andTree(f, ext) ))
  }


  def loadVector(path: String): Vocabulary = {
    val res = new Vocabulary
    for ((line,i)  <- Source.fromFile(path).getLines().zipWithIndex) {
      val a :: b :: _ = line.split(' ').toList
      res(a) = b.toDouble
    }
    redis.hmset(path, res.mapValues(_.toString))
    res
  }
  
  
  def saveVector(dict: Vocabulary, filePath: String) {
    val writer = new PrintWriter(new File(filePath))
    try {
      for ((word, count) <- dict) {
        writer.println(word + " " + count)
      }
    } finally {
      writer.close()
    }
  }
  
  
  def updateVector(res: Vocabulary, dict1: Vocabulary) {
    for ((word, count) <- dict1) {
      res(word) = res.getOrElse(word, 0.0) + count
    }
  }


  def vectorLength(vec: Vocabulary): Double = {
    math.sqrt(vec.values.map(x => x * x).sum)
//  vec.values.sum
  }


  def normalizeVector(vec: Vocabulary): Vocabulary = {
    val norm = vectorLength(vec)
    for ((genre, v) <- vec)
      yield (genre, v / norm)
  }

  def reverseVector(vec: Vocabulary): Vocabulary = {
    for ((genre, v) <- vec)
    yield (genre, -v)
  }


}
