package absctaction

object Abstraction {

  trait Abstract{
    type T //T is alias
    def transform(x:T):T
    val initial:T
    var current:T
  }

  class Concrete extends Abstract{
    type T = String
    def transform(x: T): T = x +" "+ x
    val initial: T = "Hi"
    var current: T = initial
  }

  def test(): Unit ={
    val c:Concrete = new Concrete
    c.current = "Concrete implementation"
    println(c.current)

    PreinitializationAndLazyInitialization.test
    AbstractTypesUsage.test
  }

}
