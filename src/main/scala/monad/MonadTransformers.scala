package monad

import cats.data.EitherT

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import cats.implicits._
object MonadTransformers {
  val bandwidth = Map(
    "server1.rockthejvm.com" -> 50,
    "server2.rockthejvm.com" -> 300,
    "server3.rockthejvm.com" -> 170
  )

  implicit val ec = ExecutionContext.global
  type AsyncResponse[T] = EitherT[Future,String,T]

  def getBandwidth(serverName:String):AsyncResponse[Int] = bandwidth.get(serverName) match {
    case Some(value) => EitherT(Future(value.asRight))
    case None => EitherT(Future(s"Server $serverName unreachable".asLeft))
  }

  def canWithstandSurge(s1:String,s2:String):AsyncResponse[Boolean] = for{
    server1Capacity <- getBandwidth(s1)
    server2Capacity <- getBandwidth(s2)
  }yield server1Capacity+server2Capacity>=250

  def generateTrafficSpikeReport(s1:String,s2:String):AsyncResponse[String] =
    canWithstandSurge(s1,s2)
      .map(theyCan=>s"Servers $s1,$s2 can${if (theyCan) "" else "not" } withstand the traffic surge")


  def main(args: Array[String]): Unit = {
    println(
      generateTrafficSpikeReport(
        "server1.rockthejvm.com",
        "server3.rockthejvm.com"
      ).value
    )
  }
}
