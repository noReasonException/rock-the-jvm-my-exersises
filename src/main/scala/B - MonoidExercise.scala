package playground

import cats.Monoid
import cats.instances.int._
import cats.syntax.semigroup._
object MonoidExersise {

  def reduceByFold[T:Monoid](list:List[T]):T=list.foldRight(cats.Monoid.apply[T].empty)(_|+|_)

  def main(args: Array[String]): Unit = {

  }

}
