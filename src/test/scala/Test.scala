import scala.collection.mutable.ArrayBuffer
import ExtFn.KtStd

object Test {
    def main(args: Array[String]): Unit = {
        val list = ArrayBuffer[Int]()
        list += 1
        list += 2
        list += 3
        println(list)
        list.let(i => i.remove(i.size - 1))
        println(list)
    }
}
