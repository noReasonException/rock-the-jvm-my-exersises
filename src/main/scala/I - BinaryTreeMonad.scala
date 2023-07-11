package playground

import cats.Monad

import scala.annotation.tailrec
import scala.util.Try

object BinaryTreeMonad{

  sealed trait Tree[+T]
  case class Leaf[+T](v:T) extends Tree[T]
  case class Branch[+T](l:Tree[T],r:Tree[T]) extends Tree[T]

  case object BinaryTreeMonad extends Monad[Tree]{
    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Leaf(v) => f(v)
      case Branch(l, r) => Branch(flatMap(l)(f),flatMap(r)(f))
    }
    override def pure[A](x: A): Tree[A] = Leaf(x)

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = ???
  }

  def main(args: Array[String]): Unit = {
    println("Hello World")
  }

}
