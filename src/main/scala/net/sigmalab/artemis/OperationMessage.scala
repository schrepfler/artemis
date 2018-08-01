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

import io.circe.JsonObject

sealed trait OperationMessage[T] {

  def id: Option[String]
  def payload: Option[T]
  def `type`: String
}

// Client messages
case class GqlConnectionInit(withPayload: Some[JsonObject]) extends OperationMessage[JsonObject] {

  override def id: Option[String] = None
  override def payload: Option[JsonObject] = withPayload
  override def `type`: String = "GQL_CONNECTION_INIT"

}

case class GqlStart(withId: Some[String], withPayload: Some[JsonObject]) extends OperationMessage[JsonObject] {

  override def id: Option[String] = withId
  override def payload: Option[JsonObject] = withPayload
  override def `type`: String = "GQL_START"
}

case class GqlStop(withId: Some[String]) extends OperationMessage[JsonObject]  {

  override def id: Option[String] = withId
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_STOP"

}

case class GqlConnectionTerminate() extends OperationMessage[JsonObject] {

  override def id: Option[String] = None
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_CONNECTION_TERMINATE"

}

// Server messages
case class GqlConnectionError(withPayload: Some[JsonObject]) extends OperationMessage[JsonObject] {

  override def id: Option[String] = None
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_CONNECTION_ERROR"

}

case class GqlConnectionAck() extends OperationMessage[JsonObject] {

  override def id: Option[String] = None
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_CONNECTION_ACK"

}

case class GqlData(withId: Some[String], withPayload: Option[JsonObject]) extends OperationMessage[JsonObject] {

  override def id: Option[String] = withId
  override def payload: Option[JsonObject] = withPayload
  override def `type`: String = "GQL_DATA"

}

case class GqlError(withId: Some[String], withPayload: Some[String]) extends OperationMessage[String] {

  override def id: Option[String] = withId
  override def payload: Option[String] = withPayload
  override def `type`: String = "GQL_ERROR"

}

case class GqlComplete(withId: Some[String]) extends OperationMessage[JsonObject] {

  override def id: Option[String] = withId
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_COMPLETE"

}

case class GqlKeepAlive() extends OperationMessage[JsonObject] {

  override def id: Option[String] = None
  override def payload: Option[JsonObject] = None
  override def `type`: String = "GQL_CONNECTION_KEEP_ALIVE"

}
