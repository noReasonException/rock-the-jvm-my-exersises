package eval

import cats.Eval

object EvaluationExersises {

  //exercise 2
  //implement the following function signature
  //defer the execution of any Eval,

  def defer[A](ev: =>Eval[A]):Eval[A]=
    Eval.later().flatMap(_=>ev)

  def deferTest(args: Array[String]): Unit = {
    defer({
      Eval.now({
        println("This is a sneaky Eval.now")
        42
      })
    })
  }


  //exercise 3
  //Implement a list reversal function using eval

  //No Eval is...
  def reverse[A](list:List[A]):List[A] =
    list match {
      case ::(head, next) => reverse(next):+head
      case Nil => Nil
    }

  //Eval is...
  def reverseE[A](list:List[A]):Eval[List[A]] = for{
    state <- Eval.later(list)
    nextState <- state match {
      case ::(head, next) => reverseE(next).map(_:+head)
      case Nil => Eval.now(Nil)
    }
  }yield nextState


  def main(args: Array[String]): Unit = {
    println(reverseE((1 to 100000).toList).value)
  }




}
