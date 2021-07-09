package me.ollie.proximitychatserver

import akka.actor.typed.ActorSystem
import akka.util.Timeout
import me.ollie.proximitychatserver.location.{Chunk, Chunks}
import org.slf4j.{Logger, LoggerFactory}

import scala.concurrent.ExecutionContextExecutor
import scala.concurrent.duration.DurationInt
import scala.io.StdIn


object Main {

  implicit val actorSystem: ActorSystem[Nothing] = ActorSystem[Nothing](Guardian(), "ProximityChat")
  implicit val executionContext: ExecutionContextExecutor = actorSystem.executionContext
  implicit val timeout: Timeout = Timeout(3.seconds)

  val logger: Logger = LoggerFactory.getLogger(getClass)

//  def main(args: Array[String]): Unit = {
//
//    val route: Route = path("hello") {
//      get {
//        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
//      }
//    }
//
//    val ip = "localhost"
//    val port = 8080
//
//    val bindingFuture = Http().newServerAt(ip, port).bind(route)
//    logger.info("Server started at http://" + ip + ":" + port)
//
//    StdIn.readLine() // waiting for user input to close
//
//    bindingFuture
//      .flatMap(_.unbind())
//      .onComplete(_ -> actorSystem.terminate())
//  }

  def main(args: Array[String]): Unit = {

    val chunks = Chunks.inRadius(new Chunk("world", 0, 0, 0), 32).map(c => (c.world, c.x, c.y, c.z))
    println("length: " + chunks.size)
    chunks.foreach(c => println(c))

    StdIn.readLine()
    actorSystem.terminate()
  }
}
