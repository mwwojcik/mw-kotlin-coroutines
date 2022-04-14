package mw.kotlin.coroutines

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import java.util.*

suspend fun main() = coroutineScope {
    //metoda niesuspendowalna woła suspendowalną dlatego musi być w scope coroutineScope
    //lub builder runBlocking
    for (i in 1..3) {
        println(giveItemWithSerial(i))
    }
}

suspend fun giveItemWithSerial(id: Int): String = coroutineScope {
    //zrównoleglam wywołanie wołam metody suspendowalne więc sam ta również musi być suspendowalna
    //metody suspendowalne muszą być wołane z wnętrza korutyny
    val nameTask = this.async { giveItemName(id) }
    val serialTask = this.async { generateSerialNumber() }
    "${nameTask.await()} => ${serialTask.await()}"
}

suspend fun giveItemName(id: Int = 0): String {
    //skoro używamy delay - musi być suspended
    delay(1000)
    return when (id) {
        1 -> "Smartfon"
        2 -> "Rower"
        else -> "Coś ładnego"
    }
}

suspend fun generateSerialNumber(): String {
    //skoro używamy delay - musi być suspended
    delay(100)
    return UUID.randomUUID().toString()
}
