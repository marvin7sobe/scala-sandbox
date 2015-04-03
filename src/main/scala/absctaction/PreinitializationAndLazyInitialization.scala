package absctaction

object PreinitializationAndLazyInitialization {

  trait RationalTrait {
    val numerArg: Int
    val denormArg: Int
    require(denormArg != 0)

    private val g = gcf(numerArg, denormArg)
    val numer = numerArg / g
    val denorm = denormArg / g

    private def gcf(a: Int, b: Int): Int = {
      if (b == 0) a else gcf(b, a % b)
    }
    override def toString = numer + " / " + denorm
  }

  trait LazyRationalTrait {
    val numerArg: Int
    val denormArg: Int
    private lazy val g = {
      require(denormArg != 0)
      gcf(numerArg, denormArg)
    }
    lazy val numer = numerArg / g
    lazy val denorm = denormArg / g

    private def gcf(a: Int, b: Int): Int = {
      if (b == 0) a else gcf(b, a % b)
    }
    override def toString = numer + " / " + denorm
  }

  object LazyDemo{
    //initialization of x will be done on first usage
    lazy val x = {println("Initialization of LazyDemo.x"); "Done"}
  }

  def test {
    //abstract fields are initialized after class initialization
    // so it causes problems if you try to perform some actions on abstract fields in class initialization
    //e.g check for 0, null, multiply field to another etc.
    //there are 2 ways to solve this problem: pre-initialization fields and lazy
    //pre-initialize fields before class initialization
    val r = new {
      val numerArg: Int = 1
      val denormArg: Int = 2
    } with RationalTrait {}
    println("Pre-initialization abstract fields example: "+r)

    object twoThirds extends {
      val numerArg: Int = 2
      val denormArg: Int = 3
    } with RationalTrait
    println("Pre-initialization abstract fields example when create object: "+twoThirds)

    val d = LazyDemo
    val x = d.x // initialization of d.x

    val rlazy = new LazyRationalTrait {
      override val denormArg: Int = 5
      override val numerArg: Int = 6
    }
    println("Lazy example of Rational: " + rlazy)
  }

}
