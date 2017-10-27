package controllers.json

import domain.{Article, Digest, Topic}
import org.joda.time.DateTime
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * JSON support for [[Digest]]
  */
object DigestJsonSupport {
  private val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

  /**
    * JSON writer for joda [[DateTime]].
    */
  val jodaDateWrites: Writes[DateTime] = (d: DateTime) => JsString(d.toString())

  /**
    * JSON writer for [[Article]].
    */
  implicit val articleWrites: Writes[Article] = (
    (__ \ "title").write[String] ~
      (__ \ "description").write[String] ~
      (__ \ "url").write[String]
    ) (unlift(Article.unapply))

  /**
    * JSON writer for [[Topic]].
    */
  implicit val topicWrites: Writes[Topic] = (
    (__ \ "name").write[String] ~
      (__ \ "orderPriority").write[Int] ~
      (__ \ "articles").write[Seq[Article]]
    ) (unlift(Topic.unapply))

  /**
    * JSON writer for [[Digest]].
    */
  implicit val digestWrites: Writes[Digest] = (
    (__ \ "id").write[Option[String]] ~
      (__ \ "title").write[String] ~
      (__ \ "publishedDate").write[DateTime](jodaDateWrites) ~
      (__ \ "createdDate").write[DateTime](jodaDateWrites) ~
      (__ \ "companyName").write[String] ~
      (__ \ "contributeTo").write[String] ~
      (__ \ "topics").write[Seq[Topic]]
    ) (unlift(Digest.unapply))
}