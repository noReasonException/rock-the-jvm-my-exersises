package semigroups

import cats.Semigroup
import cats.implicits.catsSyntaxSemigroup

import java.util.UUID

object Semigroups {
  case class Expense(id:Long,amount:Double)


  def reduceThings[T:Semigroup](list:List[T]):T=list.reduce(_|+|_)


  implicit val expenseSemigroup:Semigroup[Expense] = Semigroup.instance[Expense]((l,r)=>Expense(UUID.randomUUID().hashCode(),l.amount+r.amount))


  def main(args: Array[String]): Unit = {
    val expenses:List[Expense] = Expense(1,12)::Expense(2,34)::Expense(3,230)::Nil
    println(reduceThings(expenses))
  }

}
