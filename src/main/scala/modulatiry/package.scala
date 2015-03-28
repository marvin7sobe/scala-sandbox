import modulatiry.fruit.Fruit

//package object can be only one in package
package object modulatiry {
  //package function
  //accessible in modulatiry package
  def showFruit(fruit: Fruit) {
    import fruit._
    println(name + "s are " + color)
  }

}
