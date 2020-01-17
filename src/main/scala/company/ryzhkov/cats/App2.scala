package company.ryzhkov.cats

// import cats.Semigroup
import cats.implicits._
import cats.kernel.Semigroup
import cats.Monoid

object App2 extends App {

  val r = Monoid[Int].combine(1, Monoid[Int].empty)

  val r2 = Monoid[Int].combineAll(List(1, 2, 3))

  val r3 = Monoid[Option[Int]].combineAll(List(1.some, None, 2.some))

  println(r3)

}
