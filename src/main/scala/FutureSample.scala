import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf

object FutureSample extends App {

  def upload(doc: String): Future[Unit] = {
    Thread.sleep(500)
    Future.successful(println(s"${doc} uploaded"))
  }

  Await.result({
    for {
      _ <- upload("a")
      _ <- upload("b")
      _ <- upload("a")
    } yield ()
  }, Inf)

}


