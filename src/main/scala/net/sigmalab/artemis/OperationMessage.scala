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

sealed trait OperationMessage

// Client messages
//case class GqlConnectionInit(payload: Map[String, Any])    extends OperationMessage
//case class GqlStart(id: String, payload: Map[String, Any]) extends OperationMessage
case class stop(id: String)       extends OperationMessage
case class connection_terminate() extends OperationMessage

// Server messages
//case class GqlConnectionError(payload: Map[String, Any])   extends OperationMessage
case class connection_ack() extends OperationMessage
//case class GqlData(id: String, payload: Map[String, Any])  extends OperationMessage
case class error(id: String, payload: Map[String, Any]) extends OperationMessage
case class complete(id: String)                         extends OperationMessage
case class ka()                                         extends OperationMessage
