class CheckSumAccumulator {
  private var sum = 0

  def add(x: Byte) {
    sum += x
  }

  def add2(x: Byte): Unit = sum += x

  def checksum(): Int = ~(sum & 0xFF) + 1

  def checksum2() = {
    ~(sum & 0xFF) + 1
  }
}

//Singleton
//Companion Object for CheckSumAccumulator class
object CheckSumAccumulator {
  private var cache = Map[String, Int]()

  def calculate(string: String): Int = {
    if (cache.contains(string))
      cache(string)
    else {
      val cs = new CheckSumAccumulator
      for (c <- string)
        cs.add(c.toByte)
      val sum = cs.checksum()
      cache += (string -> sum)
      sum
    }
  }
}

