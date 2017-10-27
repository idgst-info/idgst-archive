package domain

import org.joda.time.DateTime

/**
  * Digest case class with consist of [[Article]]s with short description and the URL to the original post. Articles are
  * groups by [[Topic]]s
  *
  * @param id            [[Option]] a Digest ID
  * @param title         [[String]] Digest's title
  * @param publishedDate [[DateTime]] a date when a Digest was published
  * @param createdDate   [[DateTime]] a date when Digest was created
  * @param contributeTo  [[String]] generally an email for contributing to the digest
  * @param companyName   [[String]] generally a idgst.info
  * @param topics        [[Seq]] a collection with topics related to the Digest
  */
case class Digest(id: Option[String], title: String,
                  publishedDate: DateTime,
                  createdDate: DateTime = DateTime.now(),
                  contributeTo: String,
                  companyName: String,
                  topics: Seq[Topic])

/**
  * Case class for Topic(s) which Digest can have
  *
  * @param name          [[String]] name of the Topic
  * @param orderPriority [[Int]] specifies in which order the topic should be displayed related to the other Topic(s)
  * @param articles      [[Seq]] a collection with [[Article]]s related to the given [[Topic]]
  */
case class Topic(name: String, orderPriority: Int = 0, articles: Seq[Article])

/**
  * Case class for Articles which any [[Topic]] may have.
  *
  * @param title       [[String]] a short name of the Article
  * @param description [[String]] short description of what the Article is about
  * @param url         [[String]] URL to the original post/article in the internet
  */
case class Article(title: String, description: String, url: String)