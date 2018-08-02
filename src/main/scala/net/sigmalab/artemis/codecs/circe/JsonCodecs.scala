/*
 * Copyright 2017 Sigmalab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sigmalab.artemis.codecs.circe

import io.circe._
import io.circe.generic.extras._
import net.sigmalab.artemis._

import scala.collection.immutable

/**
  *  Ref. http://immutables.pl/2017/02/25/customizing-circes-auto-generic-derivation/
  */
object JsonCodecs extends AutoDerivation {

  implicit val operationMessageEncoder = new Encoder[OperationMessage[_]] {

    override final def apply(operationMessage: OperationMessage[_]): Json = {

      val idField: Option[(String, Json)] = operationMessage.id match {
        case Some(id) => Some("id", Json.fromString(id))
        case None => None
      }

      val payloadField: Option[(String, Json)] = operationMessage.payload match {
          case jsonObject: Some[JsonObject] => Some("payload", Json.fromJsonObject(jsonObject.get))
          case jsonString: Some[String] => Some("payload", Json.fromString(jsonString.get))
          case None => None
        }

      val typeField: Option[(String, Json)] = operationMessage.`type` match {
        case typeVal: String => Some("type", Json.fromString(typeVal))
      }

      val fields: List[Option[(String, Json)]] = List(idField, payloadField, typeField)

      val fieldsAsVarargs: Map[String, Json] = Map(fields.map{ somePair => somePair.get}:_*)

      Json.obj(
        fieldsAsVarargs.toSeq:_*
      )
    }
  }

  private val operationMessageDecoders = Map(
    "GQL_CONNECTION_KEEP_ALIVE" -> decoderGqlKeepAlive,
    "GQL_CONNECTION_ACK" -> decoderGqlConnectionAck,
    "GQL_CONNECTION_TERMINATE" -> decoderGqlConnectionTerminate,
    "GQL_COMPLETE" -> decoderGqlComplete,
    "GQL_STOP" -> decoderGqlStop,
    "GQL_ERROR" -> decoderGqlError,
    "GQL_DATA" -> decoderGqlData,
    "GQL_START" -> decoderGqlStart,
    "GQL_CONNECTION_INIT" -> decoderGqlConnectionInit,
    "GQL_CONNECTION_ERROR" -> decoderGqlConnectionError
  )

  val decoderGqlKeepAlive: Decoder[GqlKeepAlive] = for {
    ka <- Decoder[String].prepare(_.downField("type"))
  } yield GqlKeepAlive()

  val decoderGqlConnectionAck: Decoder[GqlConnectionAck] = for {
    ack <- Decoder[String].prepare(_.downField("type"))
  } yield GqlConnectionAck()

  val decoderGqlConnectionTerminate: Decoder[GqlConnectionTerminate] = for {
    terminate <- Decoder[String].prepare(_.downField("type"))
  } yield GqlConnectionTerminate()

  val decoderGqlComplete: Decoder[GqlComplete] = for {
    _id <- Decoder[String].prepare(_.downField("id"))
  } yield GqlComplete(Some(_id))

  val decoderGqlStop: Decoder[GqlStop] = for {
    _id <- Decoder[String].prepare(_.downField("id"))
  } yield GqlStop(Some(_id))

  val decoderGqlError: Decoder[GqlError] = for {
    _id <- Decoder[String].prepare(_.downField("id"))
    _payload <- Decoder[String].prepare(_.downField("payload"))
  } yield GqlError(Some(_id), Some(_payload))

  val decoderGqlData: Decoder[GqlData] = for {
    _id <- Decoder[String].prepare(_.downField("id"))
    _payload <- Decoder[JsonObject].prepare(_.downField("payload"))
  } yield GqlData(Some(_id), Some(_payload))

  val decoderGqlStart: Decoder[GqlStart] = for {
    _id <- Decoder[String].prepare(_.downField("id"))
    _payload <- Decoder[JsonObject].prepare(_.downField("payload"))
  } yield GqlStart(Some(_id), Some(_payload))

  val decoderGqlConnectionInit: Decoder[GqlConnectionInit] = for {
    _payload <- Decoder[JsonObject].prepare(_.downField("payload"))
  } yield GqlConnectionInit(Some(_payload))

  val decoderGqlConnectionError: Decoder[GqlConnectionError] = for {
    _payload <- Decoder[JsonObject].prepare(_.downField("payload"))
  } yield GqlConnectionError(Some(_payload))

  implicit val operationMessageDecoder: Decoder[OperationMessage[_]] = for {
    contextType <- Decoder[String].prepare(_.downField("type"))
    value <- operationMessageDecoders(contextType)
  } yield value

}