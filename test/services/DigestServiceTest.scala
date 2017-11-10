package services

import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest.BeforeAndAfterEach
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import repositories.DigestRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
  * Spec for [[DigestService]].
  */
class DigestServiceTest extends PlaySpec with BeforeAndAfterEach with MockitoSugar {

  private def fixture =
    new {
      val digestRepository: DigestRepository = mock[DigestRepository]
      val digestService = new DigestService(digestRepository)
    }

  "DigestService" should {
    "should return digest for specified id" in {
      // Setup
      val digestID = "123abc"
      val currentDate = DateTime.now()
      val f = fixture

      when(f.digestRepository.findById(digestID))
        .thenReturn(Future(Some(Json.obj("title" -> "title", "contribute" -> "contribute", "company name" -> "company name"))))

      // Run
      val result = Await.result(f.digestService.findById(digestID), 1.seconds)

      // Verify
      result must not(be(None) or be(null))

      verify(f.digestRepository).findById(digestID)
    }

    "should return None for specified id" in {
      // Setup
      val digestID = "123abc"
      val f = fixture

      when(f.digestRepository.findById(digestID)).thenReturn(Future(None))

      // Run
      val result = Await.result(f.digestService.findById(digestID), 1.seconds)

      // Verify
      result must not(be(null))
      result must be(None)

      verify(f.digestRepository).findById(digestID)
    }
  }
}
