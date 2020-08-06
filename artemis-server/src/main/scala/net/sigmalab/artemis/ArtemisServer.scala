package net.sigmalab.artemis

import akka.actor.{ Actor, ActorSystem, Props }
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.ws.{ BinaryMessage, Message, TextMessage, WebSocketRequest }
import akka.http.scaladsl.server.Directives.{ path, _ }
import akka.pattern.ask
import akka.stream.scaladsl.GraphDSL.Implicits._
import akka.stream.scaladsl.{ Flow, GraphDSL, Keep, Sink, Source }
import akka.stream.{ ActorMaterializer, FlowShape, OverflowStrategy }
import akka.{ Done, NotUsed }
import io.circe.{ Encoder, Json, JsonObject }
import io.circe.syntax._
import net.sigmalab.artemis.codecs.circe.JsonCodecs._
import net.sigmalab.artemis.Route.{ GetWebsocketFlow, graphqlRoute, websocketRoute }

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{ Failure, Success }

object ArtemisServer extends App {
  implicit val as = ActorSystem("artemis-server")
  implicit val am = ActorMaterializer()

  val httpPort = 4000

  Http().newServerAt("0.0.0.0", httpPort).bind(websocketRoute ~ graphqlRoute)
    .onComplete {
      case Success(value) => println(s"Artemis listening to port ${httpPort}.")
      case Failure(err)   => println(s"Artemis failed to bind port ${httpPort} to interface. ${err.getMessage}")
    }

}

object Route {

  case object GetWebsocketFlow

  implicit val as = ActorSystem("client-handler")
  implicit val am = ActorMaterializer()

  val websocketRoute =
    pathEndOrSingleSlash {
      complete("WS server is alive\n")
    } ~ path("connect") {

          val handler = as.actorOf(Props[ClientHandlerActor])
          val futureFlow = (handler ? GetWebsocketFlow)(3.seconds).mapTo[Flow[Message, Message, _]]

          onComplete(futureFlow) {
            case Success(flow) => handleWebSocketMessages(flow)
            case Failure(err)  => complete(err.toString)
          }
        }

  val graphqlRoute = pathEndOrSingleSlash {
      complete("WS server is alive\n")
    } ~ path("graphql") {

          val handler = as.actorOf(Props[ClientHandlerActor])
          val futureFlow = (handler ? GetWebsocketFlow)(3.seconds).mapTo[Flow[Message, Message, _]]

          onComplete(futureFlow) {
            case Success(flow) => handleWebSocketMessages(flow)
            case Failure(err)  => complete(err.toString)
          }
        }

}

class ClientHandlerActor extends Actor {

  implicit val as = context.system
  implicit val am = ActorMaterializer()

  val (down, publisher) = Source
    .actorRef[String](1000, OverflowStrategy.fail)
    .toMat(Sink.asPublisher(fanout = false))(Keep.both)
    .run()

  // test
  var counter = 0
  as.scheduler.schedule(0.seconds,
                        0.5.second,
                        new Runnable {
                          override def run() = {
                            counter = counter + 1
                            self ! counter
                          }
                        }
  )

  override def receive = {
    case GetWebsocketFlow =>
      val flow = Flow.fromGraph(GraphDSL.create() { implicit b =>
        val textMsgFlow = b.add(
          Flow[Message]
            .mapAsync(1) {
              case tm: TextMessage   => tm.toStrict(3.seconds).map(_.text)
              case bm: BinaryMessage =>
                // consume the stream
                bm.dataStream.runWith(Sink.ignore)
                Future.failed(new Exception("yuck"))
            }
        )

        val pubSrc = b.add(Source.fromPublisher(publisher).map(TextMessage(_)))

        textMsgFlow ~> Sink.foreach[String](self ! _)
        FlowShape(textMsgFlow.in, pubSrc.out)
      })

      sender ! flow

    case msg: GqlConnectionInit =>
      println(s"GqlConnectionInit received: ${msg}")
      down ! GqlConnectionAck

    case msg: GqlConnectionTerminate =>
      println("need to close socket now")

    case msg: GqlStart =>
      println("need to start gql now")

    case msg: GqlStop =>
      println("need to stop gql now")

    // replies with "hello XXX"
    case s: String =>
      println(s"handler actor received $s")
      down ! "Hello " + s + "!"

    // passes any int down the websocket
    case n: Int =>
      down ! GqlKeepAlive().asJson.noSpaces

    case _ =>
      println("Unsupported message type.")
      down ! GqlError(Some(""), Some("Error!"))

  }
}
