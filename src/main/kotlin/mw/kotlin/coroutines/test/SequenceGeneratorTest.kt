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


    @Test
    fun shouldReturnPower() = runTest {
        val seq= sequence<Pair<Int,Int>> {
            var previousPower=1
            var elem=1

            while (true){
                val power=previousPower*elem
                println("Generate power for item=${elem}")
                yield(Pair(elem,power))
                previousPower=power
                elem++
            }
        }

        seq.take(7).forEach {
            println("Power=>${it}")
        }
    }

}
