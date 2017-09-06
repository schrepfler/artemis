package net.sigmalab.artemis.codecs.circe

import io.circe.generic.extras.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.generic.extras.{AutoDerivation, Configuration}
import io.circe.{Decoder, Encoder}
import net.sigmalab.artemis.{OperationMessage}

/**
  *  Ref. http://immutables.pl/2017/02/25/customizing-circes-auto-generic-derivation/
  */
object JsonCodecs extends AutoDerivation {
  implicit val configuration: Configuration = Configuration.default.withDiscriminator("type")
  implicit val operationMessageEncoder: Encoder[OperationMessage] = deriveEncoder
  implicit val operationMessageDecoder: Decoder[OperationMessage] = deriveDecoder
}
