package company.ryzhkov.cats

import scala.util.Try
import scala.util.Success
import scala.util.Failure

object TryCatchExample extends App {
  val x = Try {
    2 / 4
  }

  x match {
    case Success(value)     => println(value)
    case Failure(exception) => println(exception.getMessage)
  }
}
