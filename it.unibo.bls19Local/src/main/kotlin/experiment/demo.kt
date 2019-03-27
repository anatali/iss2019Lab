package experiment

import javafx.application.Application.launch
import kotlinx.coroutines.*
//import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.coroutines.Continuation
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.system.measureTimeMillis

//http://beust.com/weblog/2015/10/30/exploring-the-kotlin-standard-library/
fun curThread() : String {
    return "thread=${Thread.currentThread().name}"
}

fun action(i : Int) { println("hello $i curThread=${curThread()}") }

var counter = 0


suspend fun CoroutineScope.massiveRun(
    action: suspend () -> Unit) {
    val n=100  //number of coroutines to launch
    val k=1000 //times an action is repeated by each coroutine
    val time = measureTimeMillis {
        val jobs = List(n) {
            launch {
                repeat(k) { action() }
            }
        }
        jobs.forEach { it.join() }
    }
    println("Completed ${n * k} actions in $time ms")
}


class CounterMsg(
    val cmd:String,val response:CompletableDeferred<Int>?=null){
}

fun CoroutineScope.counterActor1() = actor<CounterMsg> {
    var counter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when (msg) {
            //is IncCounter -> counter++
            //is GetCounter -> msg.response.complete(counter)
        }
    }
}
val c = GlobalScope.actor<CounterMsg> {
    // initialize actor's state
    for (msg in channel) {
        // process message here
    }
}
fun CoroutineScope.counterActor() = actor<CounterMsg> {
        var localCounter = 0 // actor state
        for (msg in channel) { // iterate over incoming messages
            when ( msg.cmd ) {
                "INC" -> localCounter++
                "DEC" -> localCounter--
                "GET" -> msg.response?.complete(localCounter)
                else -> throw Exception( "unknown" )
            }    }
    }

fun main() = runBlocking{
    val cpus = Runtime.getRuntime().availableProcessors();
    println("BEGINS with $cpus  cores")
    val counter = counterActor() // create the actor
    val initVal = CompletableDeferred<Int>()
    counter.send(CounterMsg("GET", initVal))
    println("Counter INITIAL VALUE=${initVal.await()}")
    GlobalScope.massiveRun {
        counter.send(CounterMsg("INC") )
    }
    val finalVal = CompletableDeferred<Int>()
    counter.send(CounterMsg("GET", finalVal))
    println("Counter FINAL VALUE= = ${finalVal.await()}")
    counter.close() // shutdown the actor
    println("ENDS ")
}
