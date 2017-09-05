package net.sigmalab.artemis

sealed trait OperationMessage

// Client messages
//case class GqlConnectionInit(payload: Map[String, Any]) extends OperationMessage
//case class GqlStart(id: String, payload: Map[String, Any]) extends OperationMessage
//case class GqlStop(id: String) extends OperationMessage
//case class GqlConnectionTerminate() extends OperationMessage

// Server messages
//case class GqlConnectionError(payload: Map[String, Any]) extends OperationMessage
case class GqlConnectionAck() extends OperationMessage
//case class GqlData(id: String, payload: Map[String, Any]) extends OperationMessage
//case class GqlError(id: String, payload: Map[String, Any]) extends OperationMessage
//case class GqlComplete(id: String) extends OperationMessage
//case class GqlConnectionKeepAlive() extends OperationMessage

case object OperationMessage