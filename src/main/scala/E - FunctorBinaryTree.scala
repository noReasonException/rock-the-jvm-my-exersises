package playground

import cats.Functor

sealed trait Tree[+T]
case class Leaf[+T](v:T) extends Tree[T]
case class Branch[+T](l:Tree[T],v:T,r:Tree[T]) extends Tree[T]

object Tree{
  implicit object binaryTreeFunctor extends Functor[Tree]{
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] = fa match {
      case Branch(l, v, r) => Branch(map(l)(f),f(v),map(r)(f))
      case Leaf(v) => Leaf(f(v))
    }
  }
}


object FunctorBinaryTree {
  def main(args: Array[String]): Unit = {
    val myBinaryTree: Tree[Int] =
      Branch(
        Branch(
          Leaf(1),2,Leaf(3)
        ),
        4,
        Leaf(5)
      )

    val transformedTree:Tree[Double] = Tree.binaryTreeFunctor.map(myBinaryTree)(_.toDouble)

    println(transformedTree)
  }
}
