package playground

import cats.Monad

import scala.annotation.tailrec
import scala.util.Try

object IdentityMonad{

  type Identity[T] = T

  case object IdentityMonad extends Monad[Identity]{
    override def flatMap[A, B](fa: Identity[A])(f: A => Identity[B]): Identity[B] = f(fa)

    @tailrec
    override def tailRecM[A, B](a: A)(f: A => Identity[Either[A, B]]): Identity[B] = f(a) match {
      case Left(v) => tailRecM(v)(f)
      case Right(v)=>v
    }

    override def pure[A](x: A): Identity[A] = x
  }

  def main(args: Array[String]): Unit = {

  }

}
