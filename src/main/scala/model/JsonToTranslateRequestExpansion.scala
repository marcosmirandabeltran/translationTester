package model

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import translation.TranslationProcessor

import scala.concurrent.Future
import scala.util.parsing.json.JSON

case class JsonToTranslateRequestExpansion(jsonBody: String,
                                           trgLng: String,
                                           expansion: Int)
    extends JsonToTranslateReq {

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
          TranslationProcessor.translate(
            ToTranslateData(segment, trgLng, expansion))
    })
  }

}
