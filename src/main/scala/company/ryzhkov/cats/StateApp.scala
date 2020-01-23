package company.ryzhkov.cats

import cats.data.State
import cats.implicits._

// sealed trait Foo

// case class ->[A, B](a: A, b: B)

trait Cat[->[_, _]] {
  def id[A]: A -> A
  def compose[A, B, C](f: B -> C, g: A -> B): A -> C
}

object StateApp extends App {

  val nextPrice: State[Int, Int] =
    State(price => (price + 1, price))

  val initInt = 1

  val xyz = for {
    a <- nextPrice
    b <- nextPrice
    c <- nextPrice
  } yield c

  val (m, n) = xyz.run(initInt).value

  println(m, n)

}
