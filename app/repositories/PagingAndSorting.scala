package repositories

import domain.PageRequest
import domain.SortingCriteria.{SortedBy, UnSorted}
import play.api.libs.json._
import reactivemongo.api.{QueryOpts, ReadPreference}
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection
import reactivemongo.play.json.collection.JsCursor._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Provide additional methods to retrieve entities using the pagination and sorting abstraction.
  */
trait PagingAndSorting {

  /**
    * MongoDB collection. Await and return the result of collection to prevent any kind of errors during
    * the first request when connection to DB is not established.
    *
    * @return [[JSONCollection]]
    */
  def collection: JSONCollection

  /**
    * Returns a Page of entities meeting the paging restriction provided in the pageRequest.
    *
    * @param pageRequest [[PageRequest]] restrictions for pagination
    * @return a page of entities
    */
  def findAll(pageRequest: PageRequest, projection: BSONDocument) = {

    type ResultType = JsObject
    val searchQuery = Json.obj()

    val sortingQuery = pageRequest.sortingCriteria match {
      case UnSorted => Json.obj()
      case SortedBy(sortDirection, sortBy) => Json.obj(sortBy -> sortDirection.toInt)
    }

    val queryOptions: QueryOpts = getQueryOptions(pageRequest)
    val content = collection.find(searchQuery)
      .options(queryOptions)
      .sort(sortingQuery)
      .projection(projection)
      .cursor[ResultType](ReadPreference.primary).jsArray(queryOptions.batchSizeN)

    createPage(content, pageRequest)
  }

  private def getQueryOptions(pageRequest: PageRequest) = {
    val pageNumber = pageRequest.pageNumber
    val pageSize = pageRequest.pageSize

    val skipN = (pageNumber - 1) * pageSize
    val queryOptions = QueryOpts(skipN = skipN, batchSizeN = pageSize, flagsN = 0)
    queryOptions
  }

  private def createPage(contentFuture: Future[JsArray], pageRequest: PageRequest): Future[JsObject] = {
    val futureCount = collection.count()
    val pageNumber = pageRequest.pageNumber
    val pageSize = pageRequest.pageSize

    for {
      content <- contentFuture
      totalDocs <- futureCount
    } yield {
      val totalPages: Int = math.ceil(totalDocs.toDouble / pageSize.toDouble).toInt
      val totalElements = totalDocs
      val last = pageNumber == totalPages
      val size = pageSize
      val number = pageNumber - 1
      val numberOfElements = pageSize
      val first = !last

      Json.obj(
        "totalPages" -> totalPages,
        "totalElements" -> totalElements,
        "last" -> last,
        "size" -> size,
        "number" -> number,
        "numberOfElements" -> numberOfElements,
        "first" -> first,
        "content" -> content
      )
    }
  }
}
