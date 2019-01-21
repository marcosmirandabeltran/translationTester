package model

import net.sf.okapi.lib.xliff2.core.Segment


case class ToTranslateRequest(src: String, trgLng: String)



object QuestionProtocol {

  import spray.json._

  case class Question(id: String, question: String)

  case class Answer(answer: String)

  /* messages */
  case object QuestionNotFound

  case object CorrectAnswer

  case object WrongAnswer


  /* json (un)marshalling */

  object Question extends DefaultJsonProtocol {
    implicit val format = jsonFormat2(ToTranslateRequest.apply)
  }



  /* implicit conversions */


}