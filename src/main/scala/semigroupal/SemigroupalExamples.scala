package semigroupal

import cats.Monad
import cats.implicits.catsStdInstancesForFuture

import scala.concurrent.ExecutionContext.global
import scala.concurrent.{ExecutionContext, Future}

object SemigroupalExamples {


  //The Idea
  //Same as semigroup, while keeping the enclosed effect
  //Semigroup : (A,A)=>A
  //Semigroupal (F[A],F[B]]=>F[(A,B)]
  //The combination is now subject to the Effect and not into the underlying value itself.
  //Example1, : Option: Option[A],Option[B] = Option[Tuple2[A,B]] if a,b defined, otherwise None
  //Example2 : Future:Future[A],Future[B] = Future[Tuple2[A,B]] if both succeed, Future.failure otherwise
  //Example3(non-trivial) : List: List[A],List[B] = List[Tuple2[A,B]] , cartesian product

  import cats.Semigroupal
  import cats.instances.option._
  import cats.instances.future._
  import cats.instances.list._
  import cats.syntax.semigroupal._
  import cats.syntax.monad._
  import cats.syntax.applicative._
  implicit val ec :ExecutionContext = global

  val semiF:Semigroupal[Future] = Semigroupal[Future]
  val semiL:Semigroupal[List] = Semigroupal[List]



  val optA :Option[Int] = Some(3)
  val optB :Option[String] = Some("fourty-two")

  val fuA :Future[Int] = Future.successful(3)
  val fuB :Future[String] = Future.successful("Hello")

  val liA :List[Int] = 3::4::5::Nil
  val liB :List[String] = "hello"::Nil


  def productWithMonads[F[_],A,B](fa:F[A],fb:F[B])(implicit monad:Monad[F]):F[(A,B)] = {
    monad.flatMap(fa)(a=>monad.map(fb)(b=>(a,b)))
  }

  def main(args: Array[String]): Unit = {
    println(optA.product(optB))
    println(fuA.product(fuB))
    println(semiL.product(liA,liB))
    println(productWithMonads(liA,liB))

  }

}
