import scala.concurrent.ExecutionContext
import java.util.concurrent.Executors

import scala.collection.immutable
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object ConcurrentDemo extends App {
  implicit val ec = new ExecutionContext {
    val threadPool = Executors.newFixedThreadPool(2)

    def execute(runnable: Runnable) {
      threadPool.submit(runnable)
    }

    def reportFailure(t: Throwable) {}
  }


  def start(n: Int) = Future {
    println(n)
    Thread.sleep(1000)
    n
  }

   def another = Future {
    (1 to 100) foreach {
      Thread.sleep(1000)
      print(_, " ")
    }
  }

  println("---------")

  val result1 = (1 to 2) map start
  val result2 = Seq(another)
  val result3 = (3 to 10) map start

  println("---------")


  Future.sequence(result1 ++ result2 ++ result3).onComplete {
    case Success(value) => println(value)
    case Failure(exception) => println(exception)
  }
}