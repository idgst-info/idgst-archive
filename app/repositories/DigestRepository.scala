package repositories

import javax.inject.Inject

import domain._
import play.api.libs.json.{JsObject, _}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, ExecutionContext, Future}

/**
  * MongoDB repository to work with Digests
  *
  * @param reactiveMongoApi [[ReactiveMongoApi]] MongoDB API
  * @param ec               implicit parameter for [[ExecutionContext]]
  */
class DigestRepository @Inject()(reactiveMongoApi: ReactiveMongoApi)
                                (implicit val ec: ExecutionContext) extends PagingAndSorting {
  private val collectionName = "digests"

  /**
    * Finds Digest in MongoDB by specified id.
    *
    * @param id [[String]] Digest to be found for
    * @return [[Future]] of [[Some]] if Digest is found for specified id, [[Future]] of [[None]] otherwise
    */
  def findById(id: String): Future[Option[JsObject]] = {
    collection.find(Json.obj("_id" -> Json.obj("$oid" -> id))).one[JsObject]
  }

  /**
    * MongoDB collection with Digests. Await and return the result of collection to prevent any kind of errors during
    * the first request when connection to DB is not established.
    *
    * @return [[JSONCollection]]
    */
  def collection: JSONCollection = Await.result(reactiveMongoApi.database.map(_.collection[JSONCollection](collectionName)), 60.seconds)

  /**
    * Finds all Digests with pagination.
    *
    * @param pageRequest [[PageRequest]] with pagination parameters
    * @return [[Future]] digests devided by pages according to the provided page request
    */
  def findWithPagination(pageRequest: PageRequest): Future[JsObject] = {
    findAll(pageRequest)
  }
}