package semigroups

import cats.Semigroup
import cats.implicits.catsSyntaxSemigroup

import java.util.UUID
import cats.implicits._
object Semigroups {
  case class Expense(id:Long,amount:Double)


  def reduceThings[T:Semigroup](list:List[T]):T=list.reduce(_|+|_)


  implicit val expenseSemigroup:Semigroup[Expense] = Semigroup.instance[Expense]((l,r)=>Expense(Math.max(l.id,r.id),l.amount+r.amount))


  def main(args: Array[String]): Unit = {
//    val expenses:List[Expense] = Expense(1,12)::Expense(2,34)::Expense(3,230)::Nil
//    println(reduceThings(expenses))

    val transformedSemigroup:Semigroup[String] = expenseSemigroup.imap(exp=>exp.id+","+exp.amount)(str=>Expense(str.split(",").head.toLong,str.split(",").tail.head.toDouble))
    print(transformedSemigroup.combine("1,2","3,4"))
  }

}
