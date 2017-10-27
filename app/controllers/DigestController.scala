package controllers

import javax.inject.Inject

import controllers.json.DigestJsonSupport
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.DigestService

import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Rest controller to manipulate with [[domain.Digest]]s.
  *
  * @param cc            The base controller components [[ControllerComponents]]
  * @param digestService [[DigestService]] to work with [[domain.Digest]]s
  */
class DigestController @Inject()(cc: ControllerComponents,
                                 digestService: DigestService) extends AbstractController(cc) {

  def find() = Action {
    Ok("Found")
  }

  /**
    * Find [[domain.Digest]] for specified id and returns is as JSON.
    *
    * @param id [[domain.Digest]] to be found for
    * @return status [[Ok]] and [[domain.Digest]] as JSON if it found, [[NotFound]] otherwise
    */
  def findById(id: String): Action[AnyContent] = Action.async {
    digestService.findById(id).map {
      case Some(x) =>
        Ok(Json.toJson(x)(DigestJsonSupport.digestWrites))
      case None => NotFound
    }
  }
}