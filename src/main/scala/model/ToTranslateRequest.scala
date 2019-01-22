package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol


  case class ToTranslateRequest(src: String, trgLng: String)
  object ToTranslateRequestSupport extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val PortofolioFormats = jsonFormat2(ToTranslateRequest)
  }




