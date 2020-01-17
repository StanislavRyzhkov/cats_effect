package company.ryzhkov.cats

import cats.effect.{Fiber, IO}
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends App {

  implicit val ctx = IO.contextShift(global)

  val io = IO {
    Thread.sleep(5000)
    "Hello!"
  }

  val io2 = io.flatMap(e => IO(println(e)))

  val fiber: IO[Fiber[IO, Unit]] = io2.start
  println("!!!")
  fiber.unsafeRunSync()
  println("!!!")

  Thread.sleep(7000)
}
