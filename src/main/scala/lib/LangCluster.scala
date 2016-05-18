package lib

import java.io.File
import scala.collection.mutable
import SkillenUtils._

class LangCluster {
  val basis = new mutable.HashMap[String, Vocabulary]
  val ortoBasis = new mutable.HashMap[String, Vocabulary]
  val sizes = new Vocabulary
  val englishVocabulary = new Vocabulary
  var englishVocabularySize = 0.0
  var englishVocabularyNorm = new Vocabulary

  def clusterNames = basis.keys

  def clusterEntropy(name: String) = math.log(sizes(name) + 1)


  def loadBasis() {
    for (ff <- andTree(new File(GENRES_DIR), ".dict")) {
      val ss = ff.getAbsolutePath
      val genre = ss.substring(GENRES_DIR.length, ss.length - ".dict".length)
      val dict = loadVector(ss)
      basis(genre) = dict
      ortoBasis(genre) = normalizeVector(dict)
//      ortoBasis(genre) = normalizeVector(for ((w,b) <- dict) yield (w, math.log(b + 1.0) + 1))
      sizes(genre) = dict.values.sum
      if (genre != "sf_heroic")
        updateVector(englishVocabulary, dict)
    }
    englishVocabularySize = englishVocabulary.values.sum
    englishVocabularyNorm = normalizeVector(englishVocabulary)
//    englishVocabularyNorm = normalizeVector(for ((w,b) <- englishVocabulary) yield (w, math.log(b + 1.0) + 1))
  }


  def calcModel(coeffs: Vocabulary): Vocabulary = {
    val res = new mutable.HashMap[String, Double]
    val sumCoeffs = coeffs.values.sum
    for {(genre, dict) <- ortoBasis
         (word, cc) <- dict } {
      res(word) = res.getOrElse(word, 0.0) + cc * coeffs.getOrElse(genre, 0.0) / sumCoeffs // * clusterEntropy(genre)
    }
    normalizeVector(res)
//    val tmpNorm = res.values.sum
//    for ((a,b) <- res)
//      yield (a, b / tmpNorm)
  }


  def calcInverseModel(coeffs: Vocabulary): Vocabulary = {
    val vec = calcModel(coeffs)
    for ((token, count) <- vec)
    yield (token, 2 * englishVocabularyNorm.getOrElse(token, 0.0) - count)
  }



  def calcCosVector(token: String): Vocabulary = {
    for ((genre, dict) <- basis)
      yield (genre, dict.getOrElse(token, 0.0) / (sizes(genre)+1))
  }

}

