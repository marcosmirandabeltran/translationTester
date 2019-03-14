package translationTester
import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives.onSuccess
import akka.testkit.TestKit
import model.{JsonToTranslateRequestExpansion, ToTranslateData}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}



class translationTesterTest extends TestKit(ActorSystem("translationTesterTest"))
with WordSpecLike
with Matchers {

  /*
  "A string translated with expansion 2" should " should be longer than the original segment" in {

    val seg1: String = "value1 how are www.google.es {12} youuu"
    val req = JsonToTranslateRequestExpansion.apply("%s {\"field1\" : \"seg1\", \"field2\" : \"value2\"}", "fra-FR", 2)

    val futures = req.toTranslate()


    onSuccess(Future.sequence(req.toTranslate())) {
      translated => {
        assert(translated.head.length > seg1.length)
      }
    }


  }
  */
}
