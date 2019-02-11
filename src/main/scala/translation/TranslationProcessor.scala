package translation

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TranslationProcessor {

  def translate(src: String, trgLng: String): Future[String] = {
    Future(System.currentTimeMillis() + src)
  }

}
