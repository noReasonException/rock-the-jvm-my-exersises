package state

import cats.data.State

object StateMonad {

  //State Signature and example
  def oldState():Unit={
    var x = 10
    x+=1
    println(s"Added 1, new result is $x")
    x*=5
    println(s"Multiplied by 10, new result is $x")
  }

  def newState():State[Int,String]={
      val add = State[Int,String](old=>(old+1,s"Added 1, new result is ${old+1}"))
      val multiply = State[Int,String](old=>(old*5,s"Multiplied by 5, new result is ${old*5}"))
      add flatMap(res1=>multiply.map((res1+_)))
  }
  //State is a monad, meaning is a homomorphism between categories. It goes from [S,A] to [S,A] meaning it goes from
  // the chosen category of your state plus result, to the same category plus result, It defines a semigroup on the state, however
  //its up to you to define the semigroup for the result itself, as shown above and below(with syntax sugar)

  def newStatev2():State[Int,String]=for{
    add <- State[Int,String](old=>(old+1,s"Added 1, new result is ${old+1}"))
    multiply <- State[Int,String](old=>(old*5,s"Multiplied by 5, new result is ${old*5}"))
  }yield (add+":"+multiply)

  //however we dont need to declare a semigroup each time, because the op will change depending on how we want to evolve the state


  //Exersise 1
  case class ShoppingCart(items:List[String],total:Double)
  def addToCart(item:String,price:Double):State[ShoppingCart,Double]=
    State(old=>{
      val newTotal = old.total+price
      val newItemList = old.items:+item
      (ShoppingCart(newItemList,newTotal),newTotal)
    })


  //Exersise 2
  //Definitions 24:24

  def main(args: Array[String]): Unit = {
    //oldState()
    //println(newState().run(10).value)
    val currentShoppingCart = ShoppingCart("Bread"::Nil,0.96)
    println(addToCart("Eggs",1.49).run(currentShoppingCart).value)
  }


}
