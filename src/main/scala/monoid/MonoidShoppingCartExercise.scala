package monoid

import cats.Monoid
import cats.implicits.catsSyntaxSemigroup

case class ShoppingCart(items:List[String],total:Double)


object ShoppingCart{
  def combine(l:ShoppingCart,r:ShoppingCart) = ShoppingCart(l.items:::r.items,r.total+l.total)
  def empty:ShoppingCart=ShoppingCart(Nil,0)
}
object MonoidShoppingCartExercise {

  def reduceByFold[T:Monoid](list:List[T]):T=list.foldRight(cats.Monoid.apply[T].empty)(_|+|_)

  implicit val shoppingCartMonoid:Monoid[ShoppingCart] = Monoid.instance(ShoppingCart.empty,ShoppingCart.combine)
  def main(args: Array[String]): Unit = {

    val shoppingCarts = List(
      ShoppingCart(
        List("A","B","C"),10
      ),
      ShoppingCart(
        List("AA","BB","CC"),100
      ),
      ShoppingCart(
        List("AAA","BBB","CCC"),1000
      )
    )

    println(reduceByFold(shoppingCarts))
  }

}


