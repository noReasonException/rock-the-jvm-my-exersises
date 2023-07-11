package playground

import cats.Monad
import cats.implicits._
object CartesianProduct {
  val numbersList = List(1,2,3)
  val charsList = List('a','b','c')



  def v1p1:Unit=println({
    for{
      a <- numbersList
      b <- charsList
    }yield a+"-"+b
  })


  val numberOption = Option(2)
  val charOption = Option('d')


  def v1p2:Unit = println({
    for{
      a <- numberOption
      b <- charOption
    }yield a+"-"+b
  })


  def generic[F[_]:Monad,A](l1:F[A],l2:F[A]):Unit=println({
    for{
      a<-l1
      b<-l2
    }yield (a,b)
  })

  def main(args: Array[String]): Unit = {
    generic(numberOption,charOption)
  }
}
