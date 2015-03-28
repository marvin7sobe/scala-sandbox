//Ordered trait is used to get possibilities to compare objects with >, <, >=, <= operators
class Rational(n: Int, d: Int) extends Ordered[Rational]{
  //check required statements when object is created in other case throws IllegalArgumentException
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer = n / g
  val denom = d / g

  //additional constructor
  //In scala each additional constructor must execute default constructor
  def this(n: Int) = this(n, 1)

  def +(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def +(n: Int) =
    new Rational(n * denom + numer, denom)

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)


  override def compare(that: Rational): Int = (this.numer * that.denom) - (that.numer * this.denom)

  override def toString: String = numer + "/" + denom
}
