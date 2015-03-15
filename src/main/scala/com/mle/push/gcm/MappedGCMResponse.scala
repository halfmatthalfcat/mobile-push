package com.mle.push.gcm

import com.mle.push.gcm.MappedGCMResponse.TokenReplacement
import play.api.libs.json.Json

/**
 * @author Michael
 */
case class MappedGCMResponse(ids: Seq[String], response: GCMResponse) {
  lazy val replacements: Seq[TokenReplacement] = {
    if (response.canonical_ids > 0) {
      for {
        (id, result) <- ids zip response.results
        canonical <- result.registration_id.toSeq
      } yield TokenReplacement(id, canonical)
    } else {
      Nil
    }
  }
}

object MappedGCMResponse {
  implicit val json = Json.reads[MappedGCMResponse]

  case class TokenReplacement(oldToken: String, newToken: String)

}
