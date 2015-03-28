package traits

import org.scalatest.FunSuite
import traits.Queues.BasicIntQueue

class QueuesSuite extends FunSuite{

  test("BasicIntQueue put(), and get()"){
    val queue = new BasicIntQueue
    queue.put(22)
    queue.put(33)
    assert(queue.get() == 22)
    assert(queue.get() == 33)
  }
}
