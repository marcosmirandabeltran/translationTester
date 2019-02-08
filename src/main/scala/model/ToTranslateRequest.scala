package model

import spray.json.DefaultJsonProtocol
import akka.http.scaladsl._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import spray.json._

  case class ToTranslateRequest(segments: List[String], trgLng: String)
  object ToTranslateRequestSupport extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val PortofolioFormats = jsonFormat2(ToTranslateRequest)
  }




