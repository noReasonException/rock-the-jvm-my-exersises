


val h = 12

def square(in: Double): Double = ???


val double:Double = 1.27d
val doublev2:Double=Double.box(1.28)


val truth:Int = if(true) 42 else 41

if(1 < 2) println("Yes") else println("No")


val theTruth:Int = {
    val halfTheTruth = 21
    halfTheTruth*2
}



def eitherVersion:Either[String,Int] = if(1 > 2) Left("alien") else Right(2001)



class Person(first: String, last: String="stefanos") {
    println("Constructor of Person object")
    val firstName = first
    val lastName = last
    def name = firstName + " " + lastName
}


val a = new Person("hello","world")


new Person(first = "Last", last = "First")


val b:Unit = ()

println(b)

