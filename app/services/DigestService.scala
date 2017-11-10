package services

import javax.inject.Inject

import domain.PageRequest
import play.api.libs.json.JsObject
import repositories.DigestRepository

import scala.concurrent.Future

/**
  * Service for working with Digests
  *
  * @param digestRepository [[DigestRepository]] to communicate with database
  */
class DigestService @Inject()(digestRepository: DigestRepository) {

  /**
    * Finds the Digest for specified id.
    *
    * @param id [[String]] digest to be find for
    * @return [[Future]] of [[Some]] if Digest is found for specified id, [[Future]] of [[None]] otherwise
    */
  def findById(id: String): Future[Option[JsObject]] = digestRepository.findById(id)


  /**
    * Finds all Digests with pagination restrictions.
    *
    * @param pageRequest [[PageRequest]] restrictions for pagination
    * @return [[Future]] of [[JsObject]] as a result
    */
  def findWithPagination(pageRequest: PageRequest): Future[JsObject] = digestRepository.findWithPagination(pageRequest)
}