import cats.effect.IO
import monix.eval.Task

import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration.Inf

class PerformanceTest extends org.scalatest.FreeSpec {

  val count = 1000000

  def futureUpload(doc: String): Future[String] = Future.successful(s"${doc} uploaded")

  def monixUpload(doc: String): Task[String] = Task(s"${doc} uploaded")

  def catsIOUpload(doc: String): IO[Unit] = IO (s"${doc} uploaded")

  "Future performance" in {
    import scala.concurrent.ExecutionContext.Implicits.global

    val futures = Seq.fill(count)(futureUpload("future"))
    val result = Future.sequence(futures)

    Await.result(result, Inf)
  }


  "Monix Task performance" in {
    import monix.execution.Scheduler.Implicits.global

    val tasks = Seq.fill(count)(monixUpload("monix"))

    Task.gather(tasks).runSyncUnsafe()
  }

  "Cats IO performance" in {
    import scala.concurrent.ExecutionContext.Implicits.global

    val ios = Seq.fill(count)(catsIOUpload("cats IO"))
    val result: Seq[Future[Unit]] = ios.map(_.unsafeToFuture())

    Await.result(Future.sequence(result), Inf)
  }

}
