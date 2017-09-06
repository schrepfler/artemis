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

package net.sigmalab.artemis

import io.circe.syntax._
import net.sigmalab.artemis.codecs.circe.JsonCodecs._

import org.scalatest.{ FlatSpec, Matchers }

class OperationMessageMarshallingTest extends FlatSpec with Matchers {

  "A connection_ack" should "be encodable to JSON" in {
    val _connection_ack: OperationMessage     = connection_ack()
    val _connection_ackJson = _connection_ack.asJson
    _connection_ackJson.noSpaces should be("""{"type":"connection_ack"}""")
  }

  "A complete" should "be encodable to JSON" in {
    val _complete: OperationMessage     = complete("completed-id")
    val completeJson = _complete.asJson
    completeJson.noSpaces should be("""{"id":"completed-id","type":"complete"}""")
  }

  "A GqlConnectionInit" should "be encodable to JSON" in {
//    val gqlConnectionInit     = GqlConnectionInit(Map())
//    val gqlConnectionInitJson = gqlConnectionInit.asJson
//    gqlConnectionInitJson.noSpaces should be("""{"type":"GqlConnectionInit"}""")
  }

  "A GqlStart" should "be encodable to JSON" in {
//    val gqlStart     = GqlStart()
//    val gqlStartJson = gqlStart.asJson
//    gqlConnectionAckJson.noSpaces should be("""{"type":"GqlStart"}""")
  }

  "A stop" should "be encodable to JSON" in {
    val gqlStop: OperationMessage     = stop("stop-id")
    val gqlStopJson = gqlStop.asJson
    gqlStopJson.noSpaces should be("""{"id":"stop-id","type":"stop"}""")
  }

  "A connection_terminate" should "be encodable to JSON" in {
    val gqlConnectionTerminate: OperationMessage     = connection_terminate()
    val gqlConnectionTerminateJson = gqlConnectionTerminate.asJson
    gqlConnectionTerminateJson.noSpaces should be("""{"type":"connection_terminate"}""")
  }

  "A error" should "be encodable to JSON" in {
//    val gqlConnectionError     = error("id", Map("error" -> "message"))
//    val gqlConnectionErrorJson = gqlConnectionError.asJson
//    gqlConnectionErrorJson.noSpaces should be("""{"type":"GqlConnectionError"}""")
  }

  "A GqlData" should "be encodable to JSON" in {
//    val gqlData     = GqlData()
//    val gqlDataJson = gqlData.asJson
//    gqlDataJson.noSpaces should be("""{"type":"GqlData"}""")
  }

  "A GqlError" should "be encodable to JSON" in {
//    val gqlError     = GqlError()
//    val gqlErrorJson = gqlError.asJson
//    gqlErrorJson.noSpaces should be("""{"type":"GqlError"}""")
  }

  "A GqlConnectionKeepAlive" should "be encodable to JSON" in {
    val gqlConnectionKeepAlive: OperationMessage     = ka()
    val gqlConnectionKeepAliveJson = gqlConnectionKeepAlive.asJson
    gqlConnectionKeepAliveJson.noSpaces should be("""{"type":"ka"}""")
  }

}
