package traits

//traits can not have custom constructor with passing params
//trait is equialent to interface in Java world
object Traits {

  abstract class Animal

  trait Philosophical{
    def philosophize() {
      println("I consume memory, therefore I am!")
    }
  }

  trait HasLegs

  //extending trait
  class Plane extends Philosophical

  //implementing few traits
  class Frog extends Animal with Philosophical with HasLegs {
    override def toString: String = "green"
    //overriding trait method
    override def philosophize() = println("It ain't easy being "+ toString +"!")
  }

  def test{
    val frog1:Philosophical = new Frog
    val frog2:Frog = new Frog
    frog2.philosophize()
  }

}
