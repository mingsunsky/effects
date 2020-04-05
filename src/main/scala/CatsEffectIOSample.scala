import cats.effect.IO
import cats.implicits._
import scala.concurrent.duration._
import scala.concurrent.duration.Duration._
import scala.concurrent.{Await, ExecutionContext}

object CatsEffectIOSample extends App {

  implicit val timer = IO.timer(ExecutionContext.global)
  def upload(doc: String): IO[Unit] = IO.sleep(500.milliseconds) *> IO (println(s"${doc} uploaded"))

  val a = upload("a")
  val b = upload("b")

  Await.result({
    for {
      _ <- a
      _ <- b
      _ <- a
    } yield ()
  }.unsafeToFuture(), Inf)
}
