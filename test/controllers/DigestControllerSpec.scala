package controllers

import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.Json
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.DigestService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

/**
  * Spec for [[DigestController]].
  */
class DigestControllerSpec extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {

  private val AWAIT_TIMEOUT = 10.seconds

  private def fixture =
    new {
      val digestServiceMock: DigestService = mock[DigestService]
      val controller = new DigestController(stubControllerComponents(), digestServiceMock)
    }

  "Digest Controller#findById" should {

    "should return a valid result with action" in {
      val f = fixture
      val currentDate = DateTime.now()
      val digestID = "123123"
      val digest = Json.obj(
        "title" -> "title",
        "contribute" -> "contribute",
        "company name" -> "company name"
      )

      when(f.digestServiceMock.findById(digestID)).thenReturn(Future(Some(digest)))

      val result = f.controller.findById(digestID).apply(FakeRequest())
      Await.ready(result, AWAIT_TIMEOUT)

      status(result) mustBe OK
      contentType(result) mustBe Some(JSON)
      contentAsJson(result) mustBe digest

      verify(f.digestServiceMock).findById(digestID)
    }

    "should return NOT found for invalid digest ID" in {
      val f = fixture
      val digestID = "123123"

      when(f.digestServiceMock.findById(digestID)).thenReturn(Future(None))

      val result = f.controller.findById(digestID).apply(FakeRequest())
      Await.ready(result, AWAIT_TIMEOUT)

      status(result) mustBe NOT_FOUND
      contentType(result) mustBe None

      verify(f.digestServiceMock).findById(digestID)
    }
  }
}