package repositories

import javax.inject.Inject

import domain.Digest
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONObjectID}
import repositories.readers.DigestBSONReader

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * Digest repository to comunicate with MongoDB
  *
  * @param reactiveMongoApi [[ReactiveMongoApi]] MongoDB API
  * @param ec               implicit parameter for [[ExecutionContext]]
  */
class DigestRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)
                                (implicit val ec: ExecutionContext) {
  private val collectionName = "digests"

  /**
    * Finds [[Digest]] in MongoDB by specified id.
    *
    * @param id [[String]] Digest to be found for
    * @return [[Future]] of [[Some]] if Digest is found for specified id, [[Future]] of [[None]] otherwise
    */
  def findById(id: String): Future[Option[Digest]] = {
    BSONObjectID.parse(id).map { objId =>
      collection.find(BSONDocument("_id" -> objId)).one[Digest]
    }.getOrElse(Future.successful(None))
  }

  implicit val digestReader: BSONDocumentReader[Digest] = DigestBSONReader

  /**
    * MongoDB collection with [[Digest]]s. Await and return the result of collection to prevent any kind of errors during
    * the first request when connection to DB is not established.
    *
    * @return [[BSONCollection]]
    */
  def collection: BSONCollection = Await.result(reactiveMongoApi.database.map(_.collection[BSONCollection](collectionName)), 60.seconds)
}