package company.ryzhkov.cats

import cats.Monad
import cats.implicits._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.data.Kleisli
import cats.data.EitherT
import scala.util.Success
import scala.util.Failure

object App3 extends App {

  def fun1(int: Int): Future[Either[String, Int]] =
    Future {
      if (int != 0) int.asRight else "oops".asLeft
    }

  def fun2(int: Int): Future[Either[String, String]] =
    Future {
      if (int + 1 == 10) Right("Good") else Left("Bad")
    }

  def fun3(i: Int) =
    for {
      a <- EitherT(fun1(i))
      b <- EitherT(fun2(a))
    } yield b

  fun3(9).value.onComplete {
    case Success(value) =>
      value match {
        case Right(value) => println(value)
        case Left(value)  => println(value)
      }

    case Failure(e) => println(e.getMessage)
  }

  Thread.sleep(2000)
}
