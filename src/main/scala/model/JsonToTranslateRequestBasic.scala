package model

import spray.json.DefaultJsonProtocol
import translation.TranslationProcessor

import scala.concurrent.Future
import scala.util.parsing.json.JSON



trait JsonToTranslateReq extends TranslationTesterRequests{
  val jsonBody: String
  val trgLng: String


  def toTranslate(): List[Future[String]]
  def processJson(jsonEntries: Map[String, Any]):  List[Future[String]]
}




 case class JsonToTranslateRequestBasic(jsonBody: String, trgLng: String) extends JsonToTranslateReq {

  def toTranslate(): List[Future[String]] = {
    val result = JSON.parseFull(jsonBody)
    result match {
      case Some(map: Map[String, Any]) => processJson(map)
      case _                           => throw new IllegalArgumentException("Expected a JSON")
    }
  }

  def processJson(jsonEntries: Map[String, Any]): List[Future[String]] = {
    jsonEntries.toList.map(entry =>
      entry match {
        case (key: String, segment: String) =>
          TranslationProcessor.translate(ToTranslateData(segment, trgLng, 0))
      })
  }

}





