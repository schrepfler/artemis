package net.sigmalab.artemis

import io.circe._
import io.circe.generic.extras.Configuration
import io.circe.generic.extras.AutoDerivation
import io.circe.generic.extras.semiauto._

/**
  *  Ref. http://immutables.pl/2017/02/25/customizing-circes-auto-generic-derivation/
  */
object json extends AutoDerivation {
    implicit val configuration: Configuration = Configuration.default.withDiscriminator("type")
//    implicit val GqlErrorEncoder: Encoder[GqlError] = deriveEncoder
//    implicit val GqlErrorDecoder: Decoder[GqlError] = deriveDecoder
//    implicit val GqlConnectionInitEncoder: Encoder[GqlConnectionInit] = deriveEncoder
//    implicit val GqlConnectionInitDecoder: Decoder[GqlConnectionInit] = deriveDecoder
//    implicit val GqlStartEncoder: Encoder[GqlStart] = deriveEncoder
//    implicit val GqlStartDecoder: Decoder[GqlStart] = deriveDecoder
//    implicit val GqlStopEncoder: Encoder[GqlStop] = deriveEncoder
//    implicit val GqlStopDecoder: Decoder[GqlStop] = deriveDecoder
//    implicit val GqlConnectionTerminateEncoder: Encoder[GqlConnectionTerminate] = deriveEncoder
//    implicit val GqlConnectionTerminateDecoder: Decoder[GqlConnectionTerminate] = deriveDecoder
//    implicit val GqlConnectionErrorEncoder: Encoder[GqlConnectionError] = deriveEncoder
//    implicit val GqlConnectionErrorDecoder: Decoder[GqlConnectionError] = deriveDecoder
    implicit val GqlConnectionAckEncoder: Encoder[GqlConnectionAck] = deriveEncoder
    implicit val GqlConnectionAckDecoder: Decoder[GqlConnectionAck] = deriveDecoder
//    implicit val GqlDataEncoder: Encoder[GqlData] = deriveEncoder
//    implicit val GqlDataDecoder: Decoder[GqlData] = deriveDecoder
//    implicit val GqlCompleteEncoder: Encoder[GqlComplete] = deriveEncoder
//    implicit val GqlCompleteDecoder: Decoder[GqlComplete] = deriveDecoder
//    implicit val GqlConnectionKeepAliveEncoder: Encoder[GqlConnectionKeepAlive] = deriveEncoder
//    implicit val GqlConnectionKeepAliveDecoder: Decoder[GqlConnectionKeepAlive] = deriveDecoder
}