package lib

import scala.io.Source
//import scala.io.Resource

class LangModel() {

  var data : Map[String, Long] = null

//  def dumpToFile(path : String) {
//    val out = Resource.fromFile(path)
//    for ((x,y) <- data) {
//      out.write(Array(x, y) mkString " ")
//    }
//
//  }

  def loadFromFile(path : String) {
    data = Source.fromFile(path).
                  getLines().
                  map(_ split ' ').
                  map(ll => (ll(0), ll(1).toLong)).
                  toMap
  }



}


