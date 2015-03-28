package modulatiry {
  package fruit {
    abstract class Fruit(val name: String, val color: String)

    package composition{
      import modulatiry.fruit.Fruits._
      class Compose{
        val t = Tomato
        //compile error as Cherry accessible only within Fruits object
        //val cherry = Cherry
      }
    }

    object Fruits {
      object Apple extends Fruit("apple", "red")
      object Pear extends Fruit("pear", "yellowish")
      object Orange extends Fruit("orange", "orange")
      //accessible in fruit package
      private[fruit] object Tomato extends Fruit("tomato", "red")
      private[this] object Cherry extends Fruit("cherry", "red")
      val menu = List(Apple,Pear,Orange,Tomato,Cherry)
    }
  }
  package navigation{
    package menu{
      import modulatiry.fruit.Fruits
      class Space {
        //import all members from Fruits
        import modulatiry.fruit.Fruits._
        val apple = Apple
        val pear = Pear
      }
      class Ship{
        //import only Apple that is renamed to AppleRenamed, and Pear from Fruits
        import modulatiry.fruit.Fruits.{Apple=>AppleRenamed, Pear}
        val apple = AppleRenamed
        val pear = Pear
      }

      object Navigation {
        def show: Unit ={
          for (f <- Fruits.menu) {
            showFruit(f)
          }
        }
      }
    }
  }

}






