import java.io._
import java.util.Date

import absctaction.Abstraction
import caseclasses.CaseClasses
import generics.{GenericsWithBounds, MyQueueTest}
import inheritance.Spiral
import traits.{Queues, Traits}

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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
    gettersAndSetters
    genericsAndInformationHiding
    abstraction
    enumeration
    implicitConversions
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

    val capitals: Map[String, String] = Map("Kyiv" -> "Ukraine", "Moscow" -> "Russia")
    for ((city, country) <- capitals) {
      println(country + " has capital " + city)
    }

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

    arrays
    arrayBuffer
    lists
    listBuffer
    tuples
    sets
    maps
  }

  private def arrays {
    val numbers = new Array[Int](2)
    numbers(0) = 10
    numbers(1) = 33
    val words = Array("one", "two", "three")
    val list = words.toList
    println("Convert array t list" + list)
  }

  private def arrayBuffer {
    val buf = new ArrayBuffer[Int]
    buf += 2
    buf += 3
    1 +=: buf
    val first = buf(0)
    val array = buf.toArray
    println("Appending and prepending to ArrayBuffer: " + array.mkString(" "))
  }

  private def lists {
    //create
    val listIsImmutable = List("how", "are", "you", "going")

    //head
    val first = listIsImmutable.head

    //tail
    val restWithoutFirst = listIsImmutable.tail

    //last, init
    //it is not a good idea to use these both because they are slower than head and tail
    val last = listIsImmutable.last
    val restWithoutLast = listIsImmutable.init

    //take
    val firstTwoElements = listIsImmutable.take(2)
    println("First 2 elements from list: " + firstTwoElements)

    //drop
    val restWithoutFirstTwoElements = listIsImmutable.drop(2)
    println("Rest without first 2 elements from list: " + restWithoutFirstTwoElements)

    //splitAt
    val splitedList: (List[String], List[String]) = listIsImmutable.splitAt(1) // //(List(how),List(are, you, going))
    println("Splited lists at index 1: " + splitedList)

    //getByIndex
    val elementAtIndex = listIsImmutable(2)
    println("Get element from list by index 2: " + elementAtIndex)

    //zipWithIndex
    val itemsWithIndex = listIsImmutable.zipWithIndex
    println("Zipped list elements with idexes: " + itemsWithIndex) // List((how,0), (are,1), (you,2), (going,3))

    //mkString
    println("Making string from list: " + listIsImmutable.mkString("{", ",", "}"))
    println("Making string from list (separator only): " + listIsImmutable.mkString(","))

    //concatenate
    val list1 = List(1, 2, 3)
    val list2 = List(4, 5)
    val concatenatedList = list1 ::: list2
    println("Concatenated list: " + concatenatedList)

    //add to begining
    val twoThree = List(2, 3)
    val oneTwoThree = 1 :: twoThree
    println("Added element to begin of list: " + oneTwoThree)

    //add to end
    val twoThreeFourth = twoThree :+ 4
    println("Added element to end of list: " + twoThreeFourth)

    //create
    val oneTwoThreeFourth = 1 :: 2 :: 3 :: 4 :: Nil
    println("Created list just appending elements to Nil: " + oneTwoThreeFourth)

    //to array
    val array = List(1, 2, 3).toArray
    println("Convert List to arrray: " + array.mkString(" "))

    //filter
    val filteredList = listIsImmutable.filter(x => x.length == 3)
    println("Filtered list: " + filteredList)

    //find
    val find: Option[String] = listIsImmutable.find(x => x.length == 3)
    println("Find first element in list: " + find.get)

    //partition
    val pair: (List[String], List[String]) = listIsImmutable.partition(x => x.length == 3)
    println("Partition list example, predicate true: " + pair._1)
    println("Partition list example, predicate false: " + pair._2)

    //exists
    listIsImmutable.exists(x => x.length == 3)

    // /:
    val l = List(11, 2, 33)
    val sum = (0 /: l)(_ + _) // 0+11+2+33
    val multiply = (1 /: l)(_ * _) // 1*11*2*33
    val string = ("" /: l)(_ + ", " + _)//, 11, 2, 33
    println(":/ operator= " + string)

    // :\ - similar to previous but /: left fold whereas :\ - right fold
    val sum1 = (l:\0)(_ + _) // 0+11+2+33
    val multiply1 = (l:\1)(_ * _) // 1*11*2*33
    val string1 = (l:\"")(_ + ", " + _)//11, 2, 33,
    println("\\: operator= " + string1)

    //sortWith
    val sorted = List(10,4,2,3,5,7,50,40).sortWith((f1,f2)=>f1<f2)
    println("Sorted list: " +sorted)

    //factory methods and methods from Object class
    val secondItem1 = listIsImmutable(2)
    val secondItem2 = listIsImmutable.apply(2)
    val fromOneToFourth = List.range(1,5)
    val fromOneToNineWithStepTwo = List.range(1,10,2)
    val filled1 = List.fill(5)("hello") // 5 elements of "hello"
    val filled2 = List.fill(2, 3)('b') //List(List(b, b, b), List(b, b, b))
  }

  private def listBuffer {
    val buf = new ListBuffer[Int]
    buf += 2
    buf += 3
    1 +=: buf // adds 1 to the first position ibn buffer
    val first = buf(0)
    val list = buf.toList
    println("Appending and prepending to ListBuffer is done in constant time: " + list)
  }

  private def tuples {
    //Tuple
    println("\nTuple contains elements of  different types:")
    val tupleContainsDifferentObjects = ("first item has 1 index", 2, true)
    println(tupleContainsDifferentObjects._1)
    println(tupleContainsDifferentObjects._2)
    println(tupleContainsDifferentObjects._3)

    val (a, b, c) = tupleContainsDifferentObjects
    println("Pattern match example for tuple: " + a + " " + b + " " + c)
  }

  private def sets {
    println("Set example:")
    //scala has mutable and immutable versions of Set and Map
    //by default when you write val s = Set() you are getting immutable Set instance (the same applied to maps)
    //immutable means that result of add operation will return new instance with added item
    //if you want to get mutable instance you must import directly from mutable package
    import scala.collection.mutable.Set
    val mutableMoviesSet = Set("Movie1", "Movie2")
    mutableMoviesSet += "Movie3" //link to set will stay the same so we can use += method on val variable
    mutableMoviesSet.+=("Movie5")
    mutableMoviesSet -= "Movie3"
    mutableMoviesSet ++= List("Movie11", "Movie12")
    mutableMoviesSet --= List("Movie1", "Movie2")
    println("Mutable set example: " + mutableMoviesSet.mkString(" "))
    val l = mutableMoviesSet.toList

    //immutable
    var immutableCarsSet = scala.collection.immutable.Set[String]()
    immutableCarsSet += "audi" //new instance will be created and assigned to immutableCarsSet variable so we can not use var for this variable
    immutableCarsSet += "volvo"
    immutableCarsSet ++= List("volga", "slavuta")
    println("Immutable set example: " + immutableCarsSet.mkString(" "))

    //sorted
    import scala.collection.immutable.TreeSet
    val sortedSet = TreeSet(60, 41, 3, 22, 2, 11, 55, 3, 5, 3, 5, 6, 546, 95)
    println("TreeSet: " + sortedSet.mkString(" "))
  }

  private def maps {
    println("Map example:")
    import scala.collection.mutable.Map
    val map = Map[Int, String]()
    map += (0 -> "Get equipment")
    map += (1 -> "Go to island")
    map -= 1 //removes element by key
    map(1) = "Go to island by plane"
    map += (2 -> "find treasure")
    map += (3 -> "buy all what you want")
    map ++= List(4 -> "travel", 5 -> "be happy")
    map --= List(4, 5)
    map.contains(3)
    map.keySet
    map.values
    map.isEmpty
    println(map(2))

    println("Adding elements to map from constructor")
    val map2 = Map[Int, String](0 -> "Get equipment", 1 -> "Go to island")

    import scala.collection.immutable.TreeMap
    val sorted = TreeMap[Int, String](22 -> "22", 11 -> "11", 4 -> "4")
    println("TreeMap: " + sorted.mkString(", "))
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

    def describe(x: Any) = {
      x match {
        case 5 => "five"
        case true => "truth"
        case "help" => "hi"
        case _ => "something else"
      }
    }
    println(b)
    println(describe(5))
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

  def gettersAndSetters{
    class Time {
      var hour: Int = _ // assigns default value to variable. For Itn - 0, for boolean - false, for reference - null
      var minute: Int = _
    }

    var t1, t2 = new Time
    t1.hour = 12 // set hour to 12
    t1.hour_=(13) // set hour to 13
    t1.minute = 10 // set minute to 10
    println("Get time using getters: " +t1.hour+" : "+t1.minute) // get hour and minute
  }

  def genericsAndInformationHiding {
    MyQueueTest.execute
    GenericsWithBounds.test
  }

  def abstraction {
    Abstraction.test()
  }

  def enumeration {
    object Direction extends Enumeration {
      val South, East, West, North = Value
      //      val South = Value // its id=0
      //      val East = Value("east")
      //      val West = Value
      //      val North = Value
    }

    println("Get particular enumeration value: " + Direction.East)
    println("Get particular enumeration by id: " + Direction(2))
    print("Loop through enumeration values: ")
    for (e <- Direction.values) print(e + " ");
    println()
  }

  def implicitConversions {
    //implicit conversion must be in scope where conversion operation is needed
    //you can define conversions in separate object and import them
    //conversions from scala.Predef are imported automatically to each object/class
    //look at the scala.Predef.ArrowAssoc class, it makes arrow (->) syntax for map
    //only one conversion is used at a time - compiler will never rewrite x + y to convert1(convert2(x)) + y
    //if explicit code works than implicit conversion will not be used

    implicit def list2String(l: List[Any]):String = l.mkString(" ")
    val a: String = List(1, 2, 3) + " 4 5 6"
    println("Implicit conversion List to String: " + a)// 1 2 3 4 5 6

    import MyConversions.int2CharsList
    val str:String = List('0'):::123
    println("Implicit conversion Int to List of Chars and than List to String: " + str)

    import MyConversions.double2Int
    val r:Int = 3.5 // type conversion
    println("Implicit conversion Double to Int: "+ r)

    //implicit parameters
    class PrefferedDrink(val s:String)
    implicit val drink = new PrefferedDrink("tea")
    def greet(name:String)(implicit drink: PrefferedDrink): Unit ={
      println("Hi "+name+ ", would you like to drink "+ drink.s)
    }
    greet("John")

    //view bound
    //T <% Ordered[T] this is not the same as T <: Ordered[T]
    //having T <% Ordered[T] in signature you can pass for example List of Int's but Int is not subclass of Ordered[Int]
    def maxList[T <% Ordered[T]](elements: List[T]): T =
      elements match {
        case List() =>
          throw new IllegalArgumentException("empty list!")
        case List(x) => x
        case x :: rest =>
          val maxRest = maxList(rest)
          if (x > maxRest) x
          else maxRest
      }
    println("Max in List(1,2,55,66,5,4) is:"+ maxList(List(1,2,55,66,5,4)))
  }

  object MyConversions{
    implicit def int2CharsList(i:Int):List[Char] = i.toString.toCharArray.toList
    implicit def double2Int(x:Double):Int = x.toInt
  }
}
