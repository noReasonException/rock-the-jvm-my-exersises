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

  def main(args:Array[String]):Unit={
    println(countAndLog(10))
  }
}
