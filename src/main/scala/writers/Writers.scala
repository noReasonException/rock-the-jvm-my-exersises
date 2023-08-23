package writers

import cats.data.Writer

object Writers {
  def countAndSay(n:Int):Unit={
    if(n<=0) println("starting")
    else{
      countAndSay(n-1)
      println(n)
    }
  }

  def countAndLog(n:Int):Writer[Vector[String],Int]={
    for{
      cu <- Writer.apply(Vector(n.toString),n)
      next <- if(n==0) Writer.apply(Vector("Starting"),0) else countAndLog(n-1)
    }yield cu+next
  }

  def naiveSum(n:Int):Int={
    if(n<=0) 0
    else{
      val curr = naiveSum(n-1)
      println(s"n-1=$curr")
      curr+n
    }
  }
  def naiveSumWriter(n:Int):Writer[Vector[String],Int]={
    def recv(n:Writer[Vector[String],Int]):Writer[Vector[String],Int]={
      if(n.value==0) n.mapWritten(_:+"Reached Zero")
      else for {
        na <- recv(n.bimap(_:+s"Calculating ${n.value-1}",_-1))
        nb <- n.reset
      }yield nb+na
    }
    recv(Writer.apply(Vector(s"Starting ${n}"),n))
  }

  def main(args:Array[String]):Unit={
//    println(countAndLog(10))
    println(naiveSumWriter(6))
  }
}
