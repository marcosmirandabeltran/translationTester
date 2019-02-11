package routes

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
import akka.stream.scaladsl.Flow
import com.sun.org.apache.xalan.internal.xsltc.compiler.Template
import model.{ToTranslateRequest, TranslationModel}
import model.ToTranslateRequestSupport._
import model.ToTranslateRequest
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.StdIn


trait MainRoutes {


    private val route =
      get {
          path("status") {
            complete(BuildInfo.version)
          }
      }~
      post {
          entity(as[ToTranslateRequest]){ request =>
            onSuccess(
              Future.sequence(new TranslationModel(request).translate())){
              translated =>
              complete(translated)

            }
          }

      }

  def getMainRoute() ={
    route
  }

  }
