package routes

import akka.http.scaladsl.server.Directives._
import model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait MainRoutes extends JsonSupport {

  private val route =
    get {
      path("status") {
        complete(BuildInfo.version)
      }
    } ~
      post {
        {
          path("segments") {
            entity(as[ToTranslateRequest]) { request =>
              onSuccess(Future.sequence(request.toTranslate())) { translated =>
                complete(translated)
              }
            }
          } ~
            path("json") {
              //We should support request with or without expansion
              entity(as[JsonToTranslateRequestExpansion]) { request =>
                onSuccess(Future.sequence(request.toTranslate())) {
                  translated =>
                    complete(translated)
                }
              } ~
                entity(as[JsonToTranslateRequestBasic]) { request =>
                  onSuccess(Future.sequence(request.toTranslate())) {
                    translated =>
                      complete(translated)
                  }
                }
            }
        }
      }

  def getMainRoute() = {
    route
  }

}
