package com.malliina.push.gcm

import com.malliina.json.PrimitiveFormats
import com.malliina.push.{Token, TokenCompanion}
import play.api.libs.json.Json

import scala.concurrent.duration.Duration

case class GCMToken(token: String) extends AnyVal with Token

object GCMToken extends TokenCompanion[GCMToken] {
  type FCMToken = GCMToken
}

case class GCMResponse(
  multicast_id: Long,
  success: Int,
  failure: Int,
  canonical_ids: Int,
  results: Seq[GCMResult]
)

object GCMResponse {
  implicit val json = Json.reads[GCMResponse]
}

case class GCMNotification(
  title: Option[String] = None,
  body: Option[String] = None,
  subtitle: Option[String] = None,
  sound: Option[String] = None,
  badge: Option[String] = None,
  icon: Option[String] = None,
  click_action: Option[String] = None,
  body_loc_key: Option[String] = None,
  title_loc_key: Option[String] = None,
  android_channel_id: Option[String] = None,
  tag: Option[String] = None,
  color: Option[String] = None
)

object GCMNotification {
  implicit val json = Json.format[GCMNotification]
}

case class GCMLetter(
  registration_ids: Seq[GCMToken],
  data: Map[String, String],
  notification: Option[GCMNotification] = None,
  time_to_live: Option[Duration] = None,
  collapse_key: Option[String] = None,
  delay_while_idle: Option[Boolean] = None,
  restricted_package_name: Option[String] = None,
  dry_run: Option[Boolean] = None
)

object GCMLetter {
  implicit val durationFormat = PrimitiveFormats.durationFormat
  implicit val format = Json.format[GCMLetter]
}

case class GCMMessage(
  data: Map[String, String],
  notification: Option[GCMNotification] = None,
  expiresAfter: Option[Duration] = None,
  collapseKey: Option[String] = None,
  delayWhileIdle: Option[Boolean] = None,
  restrictedPackageName: Option[String] = None,
  dryRun: Option[Boolean] = None
) {
  def toLetter(ids: Seq[GCMToken]) =
    GCMLetter(
      ids,
      data,
      notification,
      expiresAfter,
      collapseKey,
      delayWhileIdle,
      restrictedPackageName,
      dryRun
    )
}

object GCMMessage {
  implicit val durationFormat = PrimitiveFormats.durationFormat
  implicit val json = Json.format[GCMMessage]

  type FCMMessage = GCMMessage
}
