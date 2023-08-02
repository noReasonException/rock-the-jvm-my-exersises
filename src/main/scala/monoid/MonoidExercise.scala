package monoid

import cats.Monoid
import cats.implicits.catsSyntaxSemigroup

object MonoidExersise {

  def reduceByFold[T:Monoid](list:List[T]):T=list.foldRight(cats.Monoid.apply[T].empty)(_|+|_)

  def main(args: Array[String]): Unit = {

  }

}
