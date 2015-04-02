package generics

//1 way to hide information
object MyQueue {
  //having apply method in object to create new instance client can call MyQueue(1,2,3) instead of MyQueue.apply(1,2,3)
  //calling private constructor
  def apply[T](elements: T*) = new MyQueue[T](elements.toList, Nil)
}

//Class with private constructor which is accessible only from itself and companion object
class MyQueue[T] private(private val leading: List[T],
                         private val trailing: List[T]) {

  //empty public constructor
  def this() = this(Nil, Nil)
  //public constructor which takes elements as ellipsis
  def this(elements: T*) = this(elements.toList, Nil)

  private def mirror = if (leading.isEmpty) new MyQueue[T](trailing.reverse, Nil) else this
  def head = mirror.leading.head
  def tail = {
    val q = mirror
    new MyQueue[T](q.leading.tail, q.trailing)
  }
  def enqueue(x: T) = new MyQueue[T](leading, x :: trailing)
  override def toString = leading.mkString(" ") +" "+ trailing.mkString(" ")
}

//2 way to hide information - more radical
trait MyQueue2[T] {
  def head: T
  def tail: MyQueue2[T]
  def enqueue(x: T): MyQueue2[T]
}

object MyQueue2 {

  def apply[T](elements: T*):MyQueue2[T] = new MyQueue2Impl[T](elements.toList, Nil)

  private class MyQueue2Impl[T](private val leading: List[T],
                                private val trailing: List[T]) extends MyQueue2[T] {
    def mirror = if (leading.isEmpty) new MyQueue2Impl[T](trailing.reverse, Nil) else this
    def head: T = mirror.leading.head
    def tail: MyQueue2Impl[T] = {
      val q = mirror
      new MyQueue2Impl[T](q.leading.tail, q.trailing)
    }
    def enqueue(x: T) = new MyQueue2Impl[T](leading, x :: trailing)

    override def toString: String = leading.mkString(" ") + " " + trailing.mkString(" ")
  }
}