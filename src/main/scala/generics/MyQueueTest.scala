package generics

object MyQueueTest{
  def execute: Unit = {
    var mq = MyQueue(1,2,3)
    mq = mq.enqueue(4)
    println("MyQueue: "+mq)

    var mq2 = MyQueue2('a','b','c')
    mq2 = mq2.enqueue('d')
    println("MyQueue2: "+mq2)
    println("MyQueue2.tail: "+mq2.tail)
  }
}
