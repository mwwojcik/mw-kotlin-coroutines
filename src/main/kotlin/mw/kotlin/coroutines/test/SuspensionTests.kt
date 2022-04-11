package mw.kotlin.coroutines

import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


internal class SampleTest {

    @Test
    fun test2() = runTest {
        println("Before")
        //zawieszenie bieżącego wątku
        //w lambdzie kod który ma się wykonać na chwilę
        //PRZED zawieszeniem
        suspendCoroutine { cont: Continuation<Unit> ->
            //powołuję nowy wątek
            thread {
                //usypiam go na 1 s
                Thread.sleep(1000)
                //wątek się wybudza i wznawia korutynę
                cont.resume(Unit)
            }
        }
        println("After")
    }

}
