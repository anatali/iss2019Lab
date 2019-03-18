package experiment


import javafx.application.Application.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis







fun sToN( s: String, base: Int=10 ) : Int{
    var v = 0
    for( i in 0..s.length-1 ) {
        val cifra =  s[i].toInt()-48
        v = ( cifra ) + v*base
        //println( "s[$i]= ${cifra}   v=$v " )
    }
    return v
}

fun exec23( op:(Int,Int) -> Int ) : Int { return op(2,3) }

fun p2( op: ( Int ) -> Int ) : Int { return op(2) }

fun counterCreate()  : ( cmd : String ) -> Int {
    var localCounter = 0
    return {
        when (it) {
            "inc" -> ++localCounter
            "dec" -> --localCounter
            "val" -> localCounter
             else -> throw Exception( "unknown" )
        }
    }
}

suspend fun ioBoundFun(){
    val timeElapsed = measureTimeMillis {
        println("Perfoming an IO operation ...")
        kotlinx.coroutines.delay(500)
    }
    println("Done, time=$timeElapsed")
}

suspend fun activate(){
    val job1 = GlobalScope.async{
        ioBoundFun()
    }
    val job2 = GlobalScope.async{
         ioBoundFun()
    }
    println("Waiting for completion")
    val end1 = job1.await()
    val end2 = job2.await()
    println("All jobs done")
}

suspend fun channelTest(){
 val timeElapsed = measureTimeMillis {
     val n = 5
     val channel = Channel<Int>(2)

     val sender = GlobalScope.launch {
         repeat( n ) {
             channel.send(it)
             println("SENDER | sent $it")
         }
     }
     delay(500) //The receiver starts after a while ...
     val receiver = GlobalScope.launch {
         for( i in 1..n ) {
             val v = channel.receive()
             println("RECEIVER | receives $v")
         }
     }

     delay(3000)
 }
    println("Done. time=$timeElapsed")
}


class CounterMsg( val cmd : String , val replyChannel : SendChannel<Int>? = null){
}

fun counterActor() = GlobalScope.actor<CounterMsg> { //(1)
    var localCounter  = 0
    for (msg in channel) { // handle incoming messages
        when ( msg.cmd ) {
            "INC" -> localCounter++
            "DEC" -> localCounter--
            "GET" -> msg.replyChannel?.send(localCounter)
            else -> throw Exception( "unknown" )
        }
    }
}


suspend fun useTheCounter(){
    val counter = counterActor()
    println("INC")
    counter.send( CounterMsg("INC") )

    val answerChannel = Channel<Int>()

    counter.send( CounterMsg("GET", answerChannel) )

    val answer = answerChannel.receive()

    println("useTheCounter COUNTER = $answer")
}

fun getList(): List<Int> {
    val arrayList = arrayListOf(1, 5, 7)
    Collections.sort(arrayList, { x, y -> println("x=$x y=$y ${y-x}"); y-x})
    return arrayList
}

fun xxx() : Unit {
}


suspend fun suspendLogin( n: Int) =
    //withContext( th ) {
    withContext(Dispatchers.Default) { //it would block the UI
    //withContext( Dispatchers.IO ){
        //Simulate UI interaction
        println("$n) thread=${Thread.currentThread().name}") //1) thread=DefaultDispatcher-worker-1
        delay( 1000 )
        println("Login $n done")
    }

suspend fun action( n: Int){
 println("$n) thread=${Thread.currentThread().name}") //1) thread=DefaultDispatcher-worker-1
 delay( 1000 )
 println("Action $n done")
}

suspend fun actionWithContext( n: Int){
    withContext(Dispatchers.Default) {
        println("$n) thread=${Thread.currentThread().name}") //1) thread=DefaultDispatcher-worker-1
        delay(1000)
        println("ActionWithContext $n done")
    }
}

//https://ktor.io/

suspend fun inputFun( callBack : ( String ) -> Unit){
    //val timeElapsed = measureTimeMillis {
        println("Performing an input operation ...")
        kotlinx.coroutines.delay(500)
        println("Calling the callback")
        callBack("someInput")
    //}
    //println("Done, time=$timeElapsed")
}

fun getInput() : String{
    println("Input  ...")
    //kotlinx.coroutines.delay(500)
    return "myinput"
}

fun submit( v: Int, msg: String ) : String{
    println("Submit ...")
    return "$msg-$v"
}

fun handle( msg: String ){
    println("Handle $msg")
}

fun doJob(n:Int){
    val s = getInput()
    val v = submit( n, s )
    handle( v )
}



fun doJobCps( n: Int  ){
    getInputAsynchCps(
        { input -> submitCps( n, input, {
                msg ->  handle( msg )
        }//handle
        )}//submitCps
    )//getInputCps
}

fun getInputCps(  callback : ( String ) -> Unit ) : Unit{
    println("Input  ...")
    //kotlinx.coroutines.delay(500)
    callback( "myinputcps" )
}

fun getInputAsynchCps(  callback : ( String ) -> Unit ) : Unit{
    kotlin.concurrent.thread(start = true) {
        println("Input  ...")
        Thread.sleep(1000)
        callback( "myinputasynchcps" )
     }
 }

fun submitCps( v: Int, msg: String , callback : ( String ) -> Unit )  {
    println("Submit ...")
    callback( "$msg-$v" )
}

/*
fun doJobCpssss( n: Int, callback : (m:String, c:(String)->Unit ) -> Unit ){
    val s = getInput()
    callback( s , c(s) )
}
*/

var counter = 0
val counterContext = newSingleThreadContext("CounterContext")
val mutex = kotlinx.coroutines.sync.Mutex()

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


fun main() = runBlocking<Unit>{
    val cpus = Runtime.getRuntime().availableProcessors();
    println("BEGINS with $cpus  cores")
    GlobalScope.massiveRun {
        mutex.withLock { counter++ }
    }
    println("ENDS with Counter = $counter")
}

val th = newSingleThreadContext("My Thread")

suspend fun launchDefault(i: Int, scope: CoroutineScope ){
    scope.launch( Dispatchers.Default ){ action(i) }
}
suspend fun launchIO(i: Int, scope: CoroutineScope ){
    scope.launch( Dispatchers.IO ){ action(i) }
}
suspend fun launchSingle(i: Int, scope: CoroutineScope ){
    scope.launch( th ){ action(i) }
}

suspend fun launchAction( i: Int, scope: CoroutineScope ){
    launchSingle(i,scope)
}
fun mainM() = runBlocking {
    val cpus = Runtime.getRuntime().availableProcessors();
    println("AT START | CPU=$cpus threads=${Thread.activeCount()} curThread=${Thread.currentThread().name}")
    println("BEGINS")
    for (i in 1..6) launchAction(i,this)//launch( Dispatchers.Default ){ action(i) }
    println("ENDS")
}