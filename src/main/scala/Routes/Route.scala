
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.io.StdIn
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directive1
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, FromMessageUnmarshaller, FromRequestUnmarshaller, Unmarshaller}
import akka.stream.ActorMaterializer
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template
import model.{ToTranslateRequest}
import spray.json.RootJsonReader
import model.ToTranslateRequestSupport._
import model.ToTranslateRequest

import scala.io.StdIn

object WebServer {
  def main(args: Array[String]) {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher



    val route =
      get {
        pathSingleSlash {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`,"<html><body>Hello world!</body></html>"))
        } ~
          path("ping") {
            complete("PONG!")
          } ~
          path("crash") {
            sys.error("BOOM!")
          }
      }~
      post {

          entity(as[ToTranslateRequest]){ toTranslateRequest =>
            complete("Post " + toTranslateRequest.segments.head)

          }


      }

    // `route` will be implicitly converted to `Flow` using `RouteResult.route2HandlerFlow`
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}