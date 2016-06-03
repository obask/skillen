package controllers

import java.io.StringReader
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.tokenattributes.{OffsetAttribute, CharTermAttribute}

object ReadTextValue {

  case class JustTerm(w: String, tr: String)

  val TEXT =
    """
      |The train from 'Frisco was very late. It should have arrived at Hugson's Siding at midnight,
      |but it was already five o'clock and the gray dawn was breaking in the east when the little train
      |slowly rumbled up to the open shed that served for the station-house.
      |As it came to a stop the conductor called out in a loud voice:
      |
      |"Hugson's Siding!"
      |
      |At once a little girl rose from her seat and walked to the door of the car,
      |carrying a wicker suit-case in one hand and a round bird-cage covered up with newspapers
      |in the other, while a parasol was tucked under her arm.
      |The conductor helped her off the car and then the engineer started his train again,
      |so that it puffed and groaned and moved slowly away up the track.
      |The reason he was so late was because all through the night there were times when
      |the solid earth shook and trembled under him, and the engineer was afraid that at any moment
      |the rails might spread apart and an accident happen to his passengers.
      |So he moved the cars slowly and with caution.
      |
    """.stripMargin



  case class RichToken(term: String, before: String, original: String)

  def tokensToRichSeq(stream: TokenStream, text: String): Seq[RichToken] = {
    var result: Seq[RichToken] = Seq()

    stream.reset()
    val charTermAttribute: CharTermAttribute = stream.addAttribute(classOf[CharTermAttribute])
    val offsetAttribute: OffsetAttribute = stream.addAttribute(classOf[OffsetAttribute])

    var previousPosition = 0
    while (stream.incrementToken) {
      result :+= RichToken(
        term=charTermAttribute.toString,
        before=text.substring(previousPosition, offsetAttribute.startOffset()),
        original=text.substring(offsetAttribute.startOffset(), offsetAttribute.endOffset())
      )
      previousPosition = offsetAttribute.endOffset()
    }
    val last = text.substring(previousPosition, text.length)
    // terminate sequence
    result :+= RichToken("", last, "")
    stream.close()
    result
  }

  private val analyzer: EnglishAnalyzer = new EnglishAnalyzer
  private val stream: TokenStream = analyzer.tokenStream("...", new StringReader(ReadTextValue.TEXT))


  //   private val analyzer: EnglishAnalyzer = new EnglishAnalyzer()

  //   private val stream: TokenStream = analyzer.tokenStream("dsadsa", "Hello I'm worker, ololo!")

//  def get(): Either[String, JustTerm] = {
//    val richSeq: Seq[RichToken] = tokensToRichSeq(stream, ReadTextValue.TEXT)
//    val map: Any = richSeq.flatMap(x =>
//      Seq(x.before, JustTerm(x.original, x.term) )
//    )
//  }



}
