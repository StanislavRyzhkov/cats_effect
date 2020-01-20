package company.ryzhkov.cats

import cats.data.Kleisli
import cats.effect.IO
import cats.implicits._
import cats.Monad

object App4 extends App {
  val x = Monad[Option].map(3.some)(_ + 1)
  println(x)

  val z: Kleisli[IO, Seq[Int], Seq[String]] =
    Kleisli(e => IO(e.map(f => (f + 1).toString())))

  val g = IO(List(3, 4))

  val h = g.flatMap(z.run)

  h.unsafeRunAsync {
    case Right(value) => println(value)
    case Left(value)  => println(value.getMessage)
  }
}

