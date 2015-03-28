package caseclasses

object CaseClasses {

  abstract class Expr

  case class Var(name: String) extends Expr

  case class Number(num: Double) extends Expr

  case class UnOp(operator: String, arg: Expr) extends Expr

  case class BinOp(operator: String, left: Expr, right: Expr) extends Expr

  def test {
    println("Case classes:")
    //new is not needed when instantiate case class
    val v = Var("a34")

    val op = BinOp("+", Number(1), v)
    //all input params get val prefix, so are treated as fields and we can refer to them
    op.operator

    //each case class has copy method. Using it new instance can be created with a bit modification
    val opCopied = op.copy(operator = "-")

    println(describe(5))
    simplifyTop(op)
  }

  def describe(x: Any) = {
    x match {
      case 5 => "five"
      case true => "truth"
      case "hellp" => "hi"
      case _ => "something else"
    }
  }

  def simplifyTop(expr: Expr): Expr = {
    val res = expr match {
      //e - any value
      //only case classes can be used in pattern patching
      case UnOp("", UnOp("", e)) => e // Double negation
      case BinOp("+", e, Number(0)) => e // Adding zero
      case BinOp("+", x, y) if x == y => BinOp("*", x, Number(2)) // pattern guard
      case BinOp("*", e, Number(1)) => e // Multiplying by one
      //_ - any object, whatever
      // if match did not find MatchError is thrown, so default should be present in this case
      case _ => expr
    }

    expr match {
      //_ - any value, whatever
      case BinOp(_, _, _) => println(expr + " is a binary operation")
      case _ => println("It's something else")
    }

    List(0, 1) match {
      case List(0, _, _) => println("list with 3 elements starting with 0") //check that list contains 3 elements and first element is 0
      case List(0, _*) => println("list starts from 0") //check that first element is 0 and other elements and their size is not important
      case _ => // can be empty
    }

    //matches any tuple with 3 params
    (1, 2, 3) match {
      case (a, b, c) => println("matched tuple " + a + b + c)
      case _ =>
    }

    //type matching
    val x: Any = "123"
    val length = x match {
      case ss: String if ss(0) == "a" => ss.length // pattern quart
      case s: String => s.length
      case sa: Array[String] => sa.length
      case ia: Array[Int] => ia.length
      case q: Int if q > 0 => q // pattern quart
      // Map type params will be erased so passing param of type Map[Int, Int] will be handled by m1 flow
      //type params is not erased with Arrays
      case m1: Map[String, Int] => m1.size
      case m2: Map[_, _] => m2.size
      case i: Int => i.toString.length
      case _ => -1
    }
    println("Type matching, length of x is:" + length)

    res
  }

}
