package model

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import translation.TranslationProcessor

import scala.concurrent.Future

case class ToTranslateRequest(segments: List[String], trgLng: String)
    extends TranslationTesterRequests {
  override def toTranslate(): List[Future[String]] = {
    (segments.map(seg =>
      TranslationProcessor.translate(ToTranslateData(seg, trgLng, 0))))
  }
}

case class ToTranslateData(src: String, trgLng: String, expansion: Int)
