package validated
import scala.util.{Either, Try}

object ValidationExamples {


  /*
    - n must be a prime
    - n must be non-negative
    - n <= 100
    - n must be even
   */

  lazy val primes = Set(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97)

  def primeCond(n:Int):Either[String,Int]=Try(assert(primes.contains(n))).toEither.left.map(_=>"PrimeCond").map(_=>n)
  def nonNegativeCond(n:Int)=Try(assert(n>0)).toEither.left.map(_=>"nonNegativeCond").map(_=>n)
  def lessThan100Cond(n:Int)=Try(assert(n<100)).toEither.left.map(_=>"lessThan100Cond").map(_=>n)
  def isEvenCond(n:Int)= Try(assert(n%2==0)).toEither.left.map(_=>"isEvenCond").map(_=>n)


  def testNumber(n:Int):Either[List[String],Int]={
    val rejectionReasons = List(primeCond _,nonNegativeCond _ ,lessThan100Cond _ ,isEvenCond _).map(_(n)).collect{
      case Left(reason) => reason
    }
    if(rejectionReasons.isEmpty)Right(n) else Left(rejectionReasons)
  }

  def main(args: Array[String]): Unit = {
    (1 to 100).toList.foreach(every=>println(testNumber(every)))
  }
}
