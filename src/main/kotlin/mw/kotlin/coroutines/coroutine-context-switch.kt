package mw.kotlin.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*

fun mainContext() = runBlocking(CoroutineName("main")) {
    val startCorName=coroutineContext[CoroutineName]?.name
    println("$startCorName => Started") // [main] Started
    val v1 = async {
        delay(500)
        val corName=coroutineContext[CoroutineName]?.name
        println("$corName => Running async") // [main] Running async
        42
    }
    launch {
        delay(1000)
        val corName=coroutineContext[CoroutineName]?.name
        println("$corName => Running launch") // [main] Running launch
    }
    val corName=coroutineContext[CoroutineName]?.name
    println("$corName => The answer is ${v1.await()}")
// [main] The answer is 42
}




fun main() = runBlocking(CoroutineName("main")) {
    val startCorName=coroutineContext[CoroutineName]?.name
    println("$startCorName => Started") // [main] Started
    val v1 = async(CoroutineName("Async SCOPE")) {
        delay(500)
        val corName=coroutineContext[CoroutineName]?.name
        println("$corName => Running async") // [main] Running async
        42
    }
    launch {
        delay(1000)
        val corName=coroutineContext[CoroutineName]?.name
        println("$corName => Running launch") // [main] Running launch
    }
    val corName=coroutineContext[CoroutineName]?.name
    println("$corName => The answer is ${v1.await()}")
// [main] The answer is 42
}
