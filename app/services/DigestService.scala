package services

import javax.inject.Inject

import domain.Digest
import repositories.DigestRepository

import scala.concurrent.Future

/**
  * Service for working with [[Digest]]s
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
  def findById(id: String): Future[Option[Digest]] = digestRepository.findById(id)
}