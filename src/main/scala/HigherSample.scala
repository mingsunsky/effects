import cats.effect._

import scala.concurrent.duration.Duration._
import scala.concurrent.duration._
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import scala.concurrent.{Await, ExecutionContext}
import cats.implicits._
import cats.effect.IO

object HigherSample extends App {

  def upload[F[_]: Timer: Sync](doc: String): F[Unit] = {
    for {
      _ <- Timer[F].sleep(500 milliseconds)
      _ <- Sync[F].pure(println(s"${doc} uploaded"))
    } yield ()
  }


  implicit val timer = IO.timer(ExecutionContext.global)
  val catsUpload: IO[Unit] = for {
    _ <- upload[IO]("cats a")
    _ <- upload[IO]("cats b")
  } yield ()

  val monixUpload: Task[Unit] = for {
    _ <- upload[Task]("monix a")
    _ <- upload[Task]("monix b")
  } yield ()



  Await.result(catsUpload.unsafeToFuture, Inf)
  Await.result(monixUpload.runToFuture, Inf)


}

