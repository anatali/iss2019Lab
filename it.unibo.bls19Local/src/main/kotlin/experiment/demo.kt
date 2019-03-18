package experiment


import javafx.application.Application.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

suspend fun CoroutineScope.massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
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
    //val cmd : String , val replyChannel : SendChannel<Int>? = null){
    val cmd : String , val response : CompletableDeferred<Int>? = null){
}
/*
fun counterActor() : SendChannel<CounterMsg> =
            GlobalScope.actor<CounterMsg> {
    var localCounter = 0 // actor state
    for (msg in channel) { // iterate over incoming messages
        when ( msg.cmd ) {
            "INC" -> localCounter++
            "DEC" -> localCounter--
            "GET" -> msg.response?.complete(localCounter)//msg.replyChannel?.send(localCounter)
            else -> throw Exception( "unknown" )
        }    }
}
*/

fun CoroutineScope.counterActor():SendChannel<CounterMsg> =
    actor<CounterMsg> {
        var localCounter = 0 // actor state
        for (msg in channel) { // iterate over incoming messages
            when ( msg.cmd ) {
                "INC" -> localCounter++
                "DEC" -> localCounter--
                "GET" -> msg.response?.complete(localCounter)
                else -> throw Exception( "unknown" )
            }    }
    }
/*
suspend fun useTheCounter(){
    val counter = counterActor()
    println("INC")
    counter.send( CounterMsg("INC") )

    val answerChannel = CompletableDeferred<Int>()
    counter.send( CounterMsg("GET", answerChannel) )

    val answer = answerChannel.await()
    println("useTheCounter COUNTER = $answer")
}
*/
fun main() = runBlocking<Unit>{
    val cpus = Runtime.getRuntime().availableProcessors();
    println("BEGINS with $cpus  cores")

    val context = newSingleThreadContext("myThread")
    val typedProducer: ReceiveChannel<Any> = produce(context) {
        send(5)
        send("a")
        send(100)
    }

    println(typedProducer.receive() ) //5
    println("-------------")
    typedProducer.consumeEach {
        println(it)
    }   //a  100


    println("ENDS ")
}

/*
fun mainM() = runBlocking {
    val cpus = Runtime.getRuntime().availableProcessors();
    println("AT START | CPU=$cpus threads=${Thread.activeCount()} curThread=${Thread.currentThread().name}")
    println("BEGINS")
    for (i in 1..6) launchAction(i,this)//launch( Dispatchers.Default ){ action(i) }
    println("ENDS")
}
        */