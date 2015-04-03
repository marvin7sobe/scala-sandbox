package absctaction

object AbstractTypesUsage {

  abstract class Food
  class Grass extends Food
  class Fish extends Food

  abstract class Animal {
    type SuitableFood <: Food
    def eat(food: SuitableFood)
  }

  class Cow extends Animal {
    override type SuitableFood = Grass
    override def eat(food: Grass): Unit = println(this.getClass.getSimpleName + " is eating food: " + food.getClass.getSimpleName)
  }

  def test: Unit ={
    val cow = new Cow
    cow.eat(new Grass)
    //cow.eat(new Fish) - this won't compile as Fish is not suitable food type for Cow
  }
}
