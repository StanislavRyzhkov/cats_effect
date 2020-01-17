package company.ryzhkov.cats

import cats.Monad
import cats.implicits._

object App4 extends App {
    val x = Monad[Option].map(3.some)(_ + 1)
    println(x)
}
