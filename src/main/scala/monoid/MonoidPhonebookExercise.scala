package monoid

import cats.Monoid
import cats.implicits.catsSyntaxSemigroup

object MonoidPhonebookExersise {
  val phoneBooks:List[Map[String,Int]]= List(
    Map(
      "Alice"->235,
      "Bob" -> 647
    ),
    Map(
      "Charlie" -> 372,
      "Daniel" -> 889
    ),
    Map(
      "Tina" -> 123
    )
  )

  def reduceByFold[T:Monoid](list:List[T]):T=list.foldRight(cats.Monoid.apply[T].empty)(_|+|_)


  def main(args: Array[String]): Unit = {
    println(reduceByFold(phoneBooks))
  }
}
