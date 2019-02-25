package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

import scala.concurrent.Future

trait TranslationTesterRequests {
  def toTranslate(): List[Future[String]]
}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val SegmentstoTranslateFormat = jsonFormat2(ToTranslateRequest)
  implicit val JsonRequestFormat = jsonFormat2(JsonToTranslateRequest)

}