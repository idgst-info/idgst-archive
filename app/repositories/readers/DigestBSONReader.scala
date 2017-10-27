package repositories.readers

import domain.{Article, Digest, Topic}
import org.joda.time.DateTime
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}

/**
  * MongoDB [[BSONDocumentReader]] for [[Digest]].
  */
object DigestBSONReader extends BSONDocumentReader[Digest] {
  implicit val topicReader: BSONDocumentReader[Topic] = TopicBSONReader

  override def read(bson: BSONDocument): Digest = {
    val id = bson.getAs[BSONObjectID]("_id").get.stringify
    val title = bson.getAs[String]("title").get
    val contributeTo = bson.getAs[String]("contributeTo").get
    val companyName = bson.getAs[String]("companyName").get
    val topics = bson.getAs[Seq[Topic]]("topics").get

    Digest(Some(id), title, DateTime.now(), DateTime.now(), contributeTo, companyName, topics)
  }
}

/**
  * MongoDB [[BSONDocumentReader]] for [[Topic]].
  */
object TopicBSONReader extends BSONDocumentReader[Topic] {
  implicit val articleReader: BSONDocumentReader[Article] = ArticleBSONReader

  override def read(bson: BSONDocument): Topic = {
    val name = bson.getAs[String]("topic").get
    val articles = bson.getAs[Seq[Article]]("articles").get

    Topic(name = name, articles = articles)
  }
}

/**
  * MongoDB [[BSONDocumentReader]] for [[Article]].
  */
object ArticleBSONReader extends BSONDocumentReader[Article] {
  override def read(bson: BSONDocument): Article = {
    val title = bson.getAs[String]("title").get
    val description = bson.getAs[String]("description").get
    val url = bson.getAs[String]("url").get

    Article(title, description, url)
  }
}