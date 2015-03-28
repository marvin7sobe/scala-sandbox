package traits

import scala.collection.mutable.ArrayBuffer

object Queues {

  abstract class IntQueue {
    def get(): Int

    def put(x: Int)
  }

  trait Doubling extends IntQueue {
    abstract override def put(x: Int): Unit = super.put(x * 2)
  }

  trait Incrementing extends IntQueue {
    abstract override def put(x: Int): Unit = super.put(x + 1)
  }

  trait Filtering extends IntQueue {
    abstract override def put(x: Int): Unit = if (x >= 0) super.put(x)
  }

  class BasicIntQueue extends IntQueue {
    private val buf = new ArrayBuffer[Int]

    override def get(): Int = buf.remove(0)

    override def put(x: Int): Unit = buf += x

    def print: Unit = buf.foreach(println)
  }

  class MyQueue extends BasicIntQueue with Doubling

  def test(){
    println("Traits examples:")
    val myqueue = new MyQueue
    myqueue.put(10)

    //is equialent to MyQueue
    val basicQueueWithDoubling = new BasicIntQueue with Doubling
    basicQueueWithDoubling.put(10)

    //returns true
    myqueue.get() == basicQueueWithDoubling.get()

    //traits ordering is important
    //firstly will be called put from Filtering trait and if it calls super than next will be called put from Incrementing trait
    //and only after that put from BasicIntQueue
    val queue = new BasicIntQueue with Incrementing with Filtering
    queue.put(-1); queue.put(0); queue.put(1)
    queue.print
  }

}
