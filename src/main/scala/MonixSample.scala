import monix.eval.Task
import scala.concurrent.duration._
import scala.concurrent.duration.Duration._
import scala.concurrent.Await
import monix.execution.Scheduler.Implicits.global

object MonixSample extends App {

  def upload(doc: String): Task[Unit] = Task(println(s"${doc} uploaded")).delayExecution(500.milliseconds)

  Await.result({
    for {
      _ <- upload("a")
      _ <- upload("b")
    } yield ()
  }.runToFuture, Inf)

}


object MonixParallel extends App {
  import MonixSample._
  val tasks = Seq(upload("a"), upload("b"), upload("c"), upload("c"), upload("d"))

  Task.gather(tasks).runSyncUnsafe()
}