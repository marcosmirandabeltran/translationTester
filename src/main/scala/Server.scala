import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

object Server extends App {
  val host = "0.0.0.0"
  val port = 9000


  implicit val system: ActorSystem = ActorSystem("Test")
  implicit val executor: ExecutionContext = system.dispatcher



}
