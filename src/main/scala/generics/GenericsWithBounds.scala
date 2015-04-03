package generics

object GenericsWithBounds {

  class Fruit(val color:String)
  class Apple(color:String, weight:Int) extends Fruit(color)
  class Pear(color:String) extends Fruit(color)
  class YellowPear extends Pear("yellow")


  //+A - covariant that means for example if Child extends Parent than Container[Child] will be subtype of Container[Parent]
  //-A - contravariant that means for example if Child extends Parent than Container[Child] will be supertype of Container[Parent]
  class Container[+A] private(private val items: List[A]) {
    def this() = this(Nil)
    //B is supertypes of A
    //lower bounds
    def put[B >: A](item: B): Container[B] = {
      new Container[B](items :+ item)
    }
    def head:A  = items.head
  }

  // T <: Fruit means T is subtypes of Fruit
  //upper bound
  def printFruitsColor[T <: Fruit](items: Set[T]) {
    for (fruit <- items) println("Fruit color from set: " + fruit.color)
  }

  def test(): Unit = {
    printFruitsColor(Set(new Apple("green", 10), new Pear("yellow")))

    var c = new Container[Pear]
    c = c.put(new Pear("light-green"))
    c = c.put(new YellowPear)
    //c = c.put(new Apple("grren", 12))// if uncomment - compile error because only supertypes of Pear can be added
    println("Fruit color from container: "+c.head.color)

  }

}
