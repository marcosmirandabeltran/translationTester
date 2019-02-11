package streams

import akka.NotUsed
import akka.stream.scaladsl.Flow
import model.ToTranslateRequest
import akka.stream.Supervision._
import akka.stream.ActorAttributes._

import scala.concurrent.Future

class TestTranslationStream {

  trait Language
  case object english extends Language
  case object french extends Language



  type TranslatedData = List[String]
  type TranslationFlowType   = Flow[ToTranslateRequest, TranslatedData, NotUsed]
  type SegmentsToBeTranslated = List[String]

  def flow() = {
    Flow[ToTranslateRequest].withAttributes(supervisionStrategy(restartingDecider)).map(request => processRequest(request))

  }

  def processRequest(toTranslateRequest: ToTranslateRequest): SegmentsToBeTranslated = {
    toTranslateRequest.segments

  }

  def translateSegment(src:String,trgLng: Language): Future[String] ={
    return ???
  }


}
