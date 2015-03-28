package inheritance

//Factory for creating different elements
//companion object for inheritance.Element
//in companion object there is access to all even private methods, variables,classes of class that it compains and visa-versa
object Element {

  //defining "val contents" variable in the constructor overrides "abstract contents" method from inheritance.Element class
  //and implementing "contents" method is not needed in this case
  private class ArrayElement(val contents: Array[String]) extends Element {}

  //LineElement will invoke constructor from his superclass (ArrayElement)
  private class LineElement(s: String) extends ArrayElement(Array(s)) {
    override def height: Int = 1

    override def width: Int = s.length
  }

  //fills the content with provided character
  private class UniformElement(c: Char, override val width: Int, override val height: Int) extends Element {
    private val line: String = c.toString * width

    override def contents: Array[String] = Array.fill(height)(line)
  }

  def elem(content: Array[String]): Element = new ArrayElement(content)

  def elem(line: String): Element = new LineElement(line)

  def elem(c: Char, width: Int, height: Int): Element = new UniformElement(c, width, height)
}


abstract class Element {

  import Element.elem

  def contents: Array[String]

  //it doesn't recommended to write () when method have no input params and doesn't performs side effects
  //when method does side affects or I/O operations
  //it is recommended to write declaration and invocation with empty parentheses e.g completeWork()
  def height: Int = contents.length

  def width: Int = if (height == 0) 0 else contents(0).length

  def above(that: Element): Element = {
    val this1 = this widen that.width
    val that1 = that widen this.width
    //++ - concatenates two arrays
    elem(this1.contents ++ that1.contents)
  }

  def beside(that: Element): Element = {
    val this1 = this heighten that.height
    val that1 = that heighten this.height

    //zip method gets corresponding elements from each array.
    // If length of one element is more that length of next element zip method skips that pair of elements
    //result of Array(1, 2, 3) zip Array("a", "b") will be Array((1, "a"), (2, "b"))
    elem(
      for ((line1, line2) <- this1.contents zip that1.contents)
        yield line1 + line2
    )
  }

  def widen(w: Int): Element =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      var right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): Element =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      var bottom = elem(' ', width, h - height - top.height)
      top above this above bottom
    }

  override def toString: String = contents mkString "\n"
}





