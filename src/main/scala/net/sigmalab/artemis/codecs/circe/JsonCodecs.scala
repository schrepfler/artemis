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

import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.extras._
import net.sigmalab.artemis._

/**
  *  Ref. http://immutables.pl/2017/02/25/customizing-circes-auto-generic-derivation/
  */
object JsonCodecs extends AutoDerivation {

  implicit val operationMessageEncoder      = new Encoder[OperationMessage[_]] {
    override final def apply(operationMessage: OperationMessage[_]): Json = Json.obj(
      ("id", Json.fromString(operationMessage.id.orNull)),
      ("payload", Json.fromJsonObject(operationMessage.payload.orNull)),
      ("type", Json.fromString(operationMessage.`type`))
    )
  }
  implicit val operationMessageDecoder      = new Decoder[OperationMessage[_]] {
    override final def apply(c: HCursor): Result[OperationMessage[_]] =
      for {
        _id <- c.downField("id").as[Option[String]]
        _payload <- c.downField("payload").as[Option[JsonObject]]
        _type <- c.downField("type").as[String]
      } yield (_id, _payload, _type) {
        _type match {
          case "GQL_CONNECTION_INIT" => _payload.fold()(payload => GqlConnectionInit(Some(payload)))
          case "GQL_STOP" => _id.fold()(id => GqlStop(Some(id)))
          case "GQL_ERROR" => _id.fold()(id => _payload.fold()(payload => GqlError(Some(id), Some(payload.toString()))))
          case "GQL_CONNECTION_KEEP_ALIVE" => GqlKeepAlive()
          case "GQL_CONNECTION_TERMINATE" => GqlConnectionTerminate()
          case "GQL_COMPLETE" => _id.fold()(id => GqlComplete(Some(id)))
          case "GQL_CONNECTION_ACK" => GqlConnectionAck()
          case "GQL_CONNECTION_ERROR" => _payload.fold()(json => GqlConnectionError(Some(json)))
          case "GQL_DATA" => _id.fold()(id => _payload.fold()(payload => GqlData(Some(id), Some(payload))))
          case "GQL_START" => _id.fold()(id => _payload.fold()(payload => GqlStart(Some(id), Some(payload))))
          case _ => DecodingFailure("Unable to decode message", c.history)
        }
      }
  }
}
