import zio._

object ZIOSample extends App {

  def upload(doc: String): Task[Unit] = {
    Task(println(s"${doc} uploaded"))
  }

  override def run(args: List[String]): ZIO[ZEnv, Nothing, Int] = {


    val a = upload("zio a")
    val b = upload("zio b")

    (for {
      _    <- a
      _    <- b
      _    <- a
    } yield ()).fold(_ => 1, _ => 0)
  }
}
