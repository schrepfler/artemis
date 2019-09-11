package net.sigmalab.artemis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{ Message, TextMessage, WebSocketRequest }
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{ Flow, Keep, Sink, Source }
import akka.{ Done, NotUsed }
import io.circe.{ Encoder, Json, JsonObject }
import io.circe.syntax._
import net.sigmalab.artemis.codecs.circe.JsonCodecs._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ArtemisClient extends App {
  implicit val as = ActorSystem("example")
  implicit val am = ActorMaterializer()

  // test client
  // print each incoming strict text message
  val printSink: Sink[Message, Future[Done]] =
    Sink.foreach { case message: TextMessage.Strict => println("client received: " + message.text) }

  val gci: GqlConnectionInit = GqlConnectionInit()

  val msg = gci.asJson.noSpaces
  val helloSource: Source[Message, NotUsed] = Source.single(TextMessage(msg))

  // the Future[Done] is the materialized value of Sink.foreach
  // and it is completed when the stream completes
  val flow: Flow[Message, Message, Future[Done]] =
    Flow.fromSinkAndSourceMat(printSink, helloSource)(Keep.left)

  // upgradeResponse is a Future[WebSocketUpgradeResponse] that
  // completes or fails when the connection succeeds or fails
  // and closed is a Future[Done] representing the stream completion from above
  val (upgradeResponse, closed) =
    Http().singleWebSocketRequest(WebSocketRequest("ws://localhost:8123/connect"), flow)

  val connected = upgradeResponse.map { upgrade =>
    // just like a regular http request we can access response status which is available via upgrade.response.status
    // status code 101 (Switching Protocols) indicates that server support WebSockets
    if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
      println("switching protocols")
      Done
    } else {
      throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
    }
  }

  // in a real application you would not side effect here
  // and handle errors more carefully
  connected.onComplete(println)
  closed.foreach(_ => println("closed"))

}
