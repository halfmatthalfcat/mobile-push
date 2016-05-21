package com.malliina.push.wns

import java.net.URL

import play.api.libs.json.{JsError, _}

import scala.util.Try
import scala.xml.Elem

case class TileBinding(template: TileTemplate,
                       texts: Seq[WnsText],
                       images: Seq[Image] = Nil,
                       groups: Seq[Group] = Nil,
                       lang: Option[String] = None,
                       baseUri: Option[URL] = None,
                       branding: Option[Branding] = None,
                       addImageQuery: Option[Boolean] = None,
                       contentId: Option[String] = None,
                       displayName: Option[String] = None,
                       hintOverlay: Option[Int] = None) extends Binding[TileTemplate]

object TileBinding {
  implicit val url = Binding.urlFormat
  implicit val json = Json.format[TileBinding]
}

case class ToastBinding(template: ToastTemplate,
                        texts: Seq[WnsText],
                        images: Seq[Image] = Nil,
                        groups: Seq[Group] = Nil,
                        lang: Option[String] = None,
                        baseUri: Option[URL] = None,
                        branding: Option[Branding] = None,
                        addImageQuery: Option[Boolean] = None,
                        contentId: Option[String] = None,
                        displayName: Option[String] = None,
                        hintOverlay: Option[Int] = None) extends Binding[ToastTemplate]

object ToastBinding {
  implicit val url = Binding.urlFormat
  implicit val json = Json.format[ToastBinding]

  def text(text: String): ToastBinding =
    ToastBinding(ToastTemplate.ToastGeneric, Seq(WnsText(text)))
}

trait Binding[T <: Template] extends Xmlable {
  def template: T

  def texts: Seq[WnsText]

  def images: Seq[Image]

  def groups: Seq[Group]

  def lang: Option[String]

  def baseUri: Option[URL]

  def branding: Option[Branding]

  def addImageQuery: Option[Boolean]

  def contentId: Option[String]

  def displayName: Option[String]

  def hintOverlay: Option[Int]

  override def xml: Elem =
    <binding>
      {texts.map(_.xml)}
      {images.map(_.xml)}
      {groups.map(_.xml)}
    </binding>.withAttributes(
      "template" -> Option(template),
      "lang" -> lang,
      "baseUri" -> baseUri,
      "branding" -> branding,
      "addImageQuery" -> addImageQuery,
      "contentId" -> contentId,
      "displayName" -> displayName,
      "hint-overlay" -> hintOverlay
    )
}

object Binding {
  val urlReader = Reads[URL](json => json.validate[String].flatMap(parseUrl))
  val urlWriter = Writes[URL](url => Json.toJson(url.toString))
  implicit val urlFormat = Format[URL](urlReader, urlWriter)
  implicit val (t, i, g) = (WnsText.json, Image.json, Group.json)

  def parseUrl(url: String) =
    Try(new URL(url))
      .map(JsSuccess(_))
      .getOrElse(JsError(s"Invalid URL: $url"))
}