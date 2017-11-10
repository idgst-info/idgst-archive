package controllers

import javax.inject.Inject

import domain.PageRequest
import domain.SortingCriteria.{SortedBy, UnSorted}
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.DigestService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Rest controller to manipulate with Digests.
  *
  * @param cc            The base controller components [[ControllerComponents]]
  * @param digestService [[DigestService]] to work with Digests
  */
class DigestController @Inject()(cc: ControllerComponents,
                                 digestService: DigestService) extends AbstractController(cc) {

  def findAll(pageNumber: Int, size: Int, sortOrderOpt: Option[String], sortByOpt: Option[String]): Action[AnyContent] = Action.async {

    val sortCriteria = for {
      sortOrder <- sortOrderOpt
      sortBy <- sortByOpt
    } yield SortedBy(sortOrder, sortBy)

    val pageRequest = PageRequest(pageNumber, size, sortCriteria.getOrElse(UnSorted))

    digestService.findWithPagination(pageRequest).map { x =>
      Ok(Json.toJson(x))
    }
  }

  /**
    * Find Digest for specified id and returns is as JSON.
    *
    * @param id of the Digest to be found for
    * @return status [[Ok]] and Digest as JSON if it found, [[NotFound]] otherwise
    */
  def findById(id: String): Action[AnyContent] = Action.async {
    digestService.findById(id).map {
      case Some(x) =>
        Ok(x)
      case None => NotFound
    }
  }
}