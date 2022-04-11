package mw.kotlin.coroutines.test

import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

class SequenceGeneratorTest {

    @Test
    fun test() = runTest {
      val seq= sequence<Int> {
          while (true){
              println("Generate item")
              yield(Random.nextInt(0,100))
          }
      }

        val it=seq.iterator()

        println("Number 1=>${it.next()}")
        println("Number 2=>${it.next()}")
        println("Number 3=>${it.next()}")

    }
}
