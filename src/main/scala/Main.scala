import java.io._
import java.util.Date

import caseclasses.CaseClasses
import inheritance.Spiral
import traits.{Queues, Traits}

object Main {
  def main(args: Array[String]): Unit = {
    rationalNumbers
    loops
    collections
    matchOperator
    caseClassesAndPatternMatching
    exceptionsHandling
    closuresAndHighOrderFunction
    classesHierarchy
    traits
    inheritance
    importsAndPackages
  }

  def rationalNumbers {
    //for conversion from int to Rational e.g. 2 + new Rational(2)
    implicit def intToRational(x: Int) = new Rational(x)

    println("Rational numbers:")
    val r1 = new Rational(2, 3)
    val r2 = new Rational(4, 5)
    val r3 = r1 + r2
    println(r1.toString + " + " + r2.toString + " = " + r3)

    val r4 = 2 + r3
    println(2.toString + " + " + r3.toString + " = " + r4.toString)

    val r5 = r1 * r4
    println(r1.toString + " * " + r4.toString + " = " + r5.toString + "\n")
  }

  def loops {
    println("Loops examples (it is recommended to use recursion instead for/while loops):")

    val days = Array("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")
    print("Days ('for' loop): ")
    for (day <- days)
      print(day + " ")

    print("\nDays ('foreach' loop): ")
    //days.foreach(print)
    days.foreach(day => print(day + " "))

    println("\nFor loop with condition, *.scala files in './src/main/scala/' directory: ")
    val files = new File("./src/main/scala/").listFiles()
    for (file <- files
         if file.isFile
         if file.getName.endsWith(".scala")) {
      println(file.getName)
    }

    println("For loop with condition and additional loop and argument")
    def getFileLines(f: File) = scala.io.Source.fromFile(f).getLines().toList
    for (file <- files
         if file.isFile
         if file.getName.endsWith(".scala"); line <- getFileLines(file)) {
      //println(file.getName + ": " + line)
    }
    println("For loop with condition and yield statement that combines files to array")
    val filesArray = for (file <- files if file.getName.startsWith("Ma")) yield file

    println("For loop with yield statement that stores return statement from loop to Sequence")
    // Returns a row as a sequence
    def makeRowSeq(row: Int) =
      for (col <- 1 to 10) yield {
        val prod = (row * col).toString
        val padding = " " * (4 - prod.length)
        padding + prod
      }

    // Returns a row as a string
    def makeRow(row: Int) = makeRowSeq(row).mkString

    // Returns table as a string with one row per line
    val multiplyTable = (for (row <- 1 to 10) yield makeRow(row)).mkString("\n")
    println(multiplyTable)

    //loop statement from 1 to 9
    for (i <- 1 until 10) i


    println("While loop example:")
    var a = 0
    while (a < 10) {
      //perform actions
      print(a + " ")
      a += 1
    }

    println("\nDo while loop example:")
    var b = 0
    do {
      //perform actions
      print(b + " ")
      b += 1
    } while (b < 10)
  }

  def collections {
    println("Arrays and Collections:")
    //Arrays
    val numbers = new Array[Int](2)
    numbers(0) = 10
    numbers(1) = 33
    val words = Array("one", "two", "three")

    //Lists
    val listIsImmutable = List("how", "are", "you", "going")

    val list1 = List(1, 2, 3)
    val list2 = List(4, 5)
    val concatenatedList = list1 ::: list2
    println("Concatenated list:")
    concatenatedList.foreach(x => print(x + " "))

    val twoThree = List(2, 3)
    println("\nAdding element to list:")
    val oneTwoThree = 1 :: twoThree
    oneTwoThree.foreach(x => print(x + " "))

    println("\nCreating list just adding elements to Nil:")
    val oneTwoThreeFourth = 1 :: 2 :: 3 :: 4 :: Nil
    oneTwoThreeFourth.foreach(x => print(x + " "))

    //Tuple
    println("\nTuple contains elements of  different types:")
    val tupleContainsDifferentObjects = ("first item has 1 index", 2, true)
    println(tupleContainsDifferentObjects._1)
    println(tupleContainsDifferentObjects._2)
    println(tupleContainsDifferentObjects._3)

    //Set
    println("Set example:")
    import scala.collection.mutable.Set
    var moviesSet = Set("Movie1", "Movie2")
    moviesSet = moviesSet + "Movie3"
    moviesSet += "Movie4"
    moviesSet.+=("Movie5")
    moviesSet.foreach(x => print(x + " "))

    //Map
    println("\nMap example:")
    import scala.collection.mutable.Map
    val map = Map[Int, String]()
    map += (0 -> "Get equipment")
    map += (1 -> "Go to island")
    map += (2 -> "find treasure")
    map += (3 -> "buy all what you want")
    println(map(2))

    println("Adding elements to map from constructor")
    val map2 = Map[Int, String](0 -> "Get equipment", 1 -> "Go to island")
  }

  def matchOperator {
    println("Match (this is 'switch' operator in scala) operator example:")
    val a = "chips"

    a match {
      case "salt" => println("pepper") //break operator is implicit
      case "chips" => println("salsa")
      // if match did not find MatchError is thrown, so default should be present in this case
      case _ => println("default value")
    }

    //match returns value
    val b = a match {
      case "salt" => "pepper"
      case "chips" => "salsa"
      // if match did not find MatchError is thrown, so default should be present in this case
      case _ => "default value"
    }
    println(b)
  }

  def caseClassesAndPatternMatching {
    CaseClasses.test
  }

  def exceptionsHandling {
    println("\nException Handling examples")
    //usual e.g. of using try catch finally block
    val f: FileReader = null
    try {
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => {
        //handle FileNotFoundException
      }
      case ex: IOException => {
        //handle IOException
      }
    } finally {
      if (f != null) f.close
    }

    //1 will be set to value variable
    val value = try {
      1
    } finally {
      2
    }
    println("In usual way try returns value (not finally) so it can be assigned to variable: " + value)

    def g(): Int = try {
      return 1
    } finally {
      return 2
    }

    //2 will be set
    val valueFromFunc = g()
    println("If try is result of execution function value will be returned from finally: " + valueFromFunc)
  }

  def closuresAndHighOrderFunction() {
    val func = (x: Int) => x + 1
    val aIs13 = func(12)

    val items = List(1, 2, 3, 4)
    items.foreach(x => print(func(x)))
    println

    items.filter(_ > 1).foreach(print)
    println

    val func2 = (_: Int) + (_: Int)
    println(func2(11, 12))

    List("a", "x", "c").foreach(print _)
    println

    def sum(a: Int, b: Int, c: Int) = a + b + c
    val a = sum _
    println(a(10, 11, 12))

    val b = sum(10, _: Int, 12)
    println(b(11))

    var s = 0
    List(10, 11, 12, 13, 14).foreach(s += _)
    println("Sum of elemtns in list = " + s)

    //storing more variable in clousure
    def makeIncrease(more: Int) = (x: Int) => x + more
    val inc1 = makeIncrease(1)
    val inc999 = makeIncrease(999)
    println(inc1(10))
    println(inc999(10))

    println("\nEllipsis example:")
    def echo(args: String*) {
      for (arg <- args) {
        print(arg + " ")
      }
    }
    echo("Life", "is", "good")
    val array = Array("\nWhat", "is", "up", "doc?")
    //to append array as ellipsis. Passing not 1 array argument but each item of array as new param
    echo(array: _*)

    println("\nNaming params to function:")
    def speed(distance: Float, time: Float) = {
      distance / time
    }
    speed(100, 10)
    speed(time = 10, distance = 120)
    speed(distance = 120, time = 10)

    def funcWithDefaultParam(x: String, y: Int = 34) = {
      x * y
    }
    funcWithDefaultParam("xyz")

    println("Examples of using high-order functions")
    FileMatcher.filesEnding(".scala")
    //is negative numbers present in list using callback in exists
    List(1, 2, -33, 3, 4, 44).exists(_ < 0)
    //is odd numbers present in list
    List(1, 2, -33, 3, 4, 44).exists(_ % 2 == 1)

    curryingExample()
    def curryingExample() {
      def sum(x: Int)(y: Int) = x + y
      val sum1 = sum(2)(3)

      def first(x: Int) = (y: Int) => x + y
      val second = first(2)
      val sum2 = second(3)
      val twoPlus = sum(2) _
      val sum3 = twoPlus(3)
      println("Calculating sum using currying technic sum1 = " + sum1 + " sum2 = " + sum2 + " sum3 = " + sum3)
    }

    withPrintWriter(new File("target/date.txt"), writer => writer.println(new Date))
    //withPrintWriter(new File("target/date.txt"), _.println(new Date))
    def withPrintWriter(f: File, op: PrintWriter => Unit) {
      val writer = new PrintWriter(f)
      try {
        op(writer)
      } finally {
        writer.close()
      }
    }

    //if method takes one argument surrounding () can be replaced with {}
    // e.g. println {"this is example"}
    //so using this approach we can simplify invocation of withPrintWriter
    val file: File = new File("target/date.txt")
    withPrintWriter2(file) {
      _.println(new Date())
    }
    def withPrintWriter2(f: File)(op: PrintWriter => Unit) {
      val writer = new PrintWriter(f)
      try {
        op(writer)
      } finally {
        writer.close()
      }
    }

    //old version
    //def myAssert(predicate: () => Boolean) = {
    //  if (predicate()) throw new AssertionError()
    //}
    //myAssert(() => 5 > 3)

    //new version
    def myAssert(predicate: => Boolean) = {
      if (!predicate) throw new AssertionError()
    }
    myAssert(5 > 3)
  }

  def classesHierarchy {
    //at the begging of scala hierarchy is Any class
    //this class extends AnyVal and AnyRef classes
    //AnyVal is superclass for build-in classes: Int, Long, Float, Double, Boolean, Char, Unit, Byte, Short
    //AnyRef is superclass for any scala class except build-in
    //Java's classes extends AnyRef class e.g. java.lang.String
    //at the bottom of hierarchy is Null class
    //Null is subclass of every reference class e.g List, Seq, Iterable,..
    //Null is type reference of null
    //Nothing is the subclass of every other type
    //Nothing almost is not used. One proper vay of using it is present is library e.g.
    //def error(message: String): Nothing = throw new RuntimeException(message)
    //what means that error method will not returns normally
    val str: String = "abcd"
    val a = str.substring(2)
    val b = str.substring(2)
    var isEqual = a == b //the same as isValuesEqual = a.equals(b)
    var isReferencesEquals = a.eq(b)
    println("Is values of a=" + a + " and b=" + b + " equals:" + isEqual + " is equals their references:" + isReferencesEquals)

    val aa: AnyRef = a
    val bb: AnyRef = b
    isEqual = aa == bb
    isReferencesEquals = aa.eq(bb)
    println("Is values of aa=" + aa + " and bb=" + bb + " equals:" + isEqual + " is equals their references:" + isReferencesEquals)

    val i: Any = 3
    val j: AnyVal = 3
    val res = i == j
    println("Is equal i:Any=" + i + " and j:AnyVal=" + j + " :" + res)
  }

  def traits {
    Traits.test
    Queues.test
  }

  def inheritance {
    println(Spiral.drawFigure(12, 0))
  }

  def importsAndPackages {
    import modulatiry.navigation.menu.Navigation
    Navigation.show
  }
}
