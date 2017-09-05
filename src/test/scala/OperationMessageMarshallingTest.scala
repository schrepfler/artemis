import net.sigmalab.artemis.{GqlConnectionAck, json}
import org.scalatest.{FlatSpec, Matchers}
import io.circe.syntax._

class OperationMessageMarshallingTest extends FlatSpec with Matchers {

  "A GqlConnectionAck" should "be encodable to JSON" in {
    import json._
    val gqlConnectionAck = GqlConnectionAck()
    val gqlConnectionAckJson = gqlConnectionAck.asJson
    gqlConnectionAckJson.noSpaces should be ("""{"type":"GqlConnectionAck"}""")
  }

}
