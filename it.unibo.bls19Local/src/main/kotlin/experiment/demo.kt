package experiment


import javafx.application.Application.launch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import java.util.*
import kotlin.system.measureTimeMillis


var counter = 0 //type inferred
fun square(v: Int) = v * v

fun incCounter() : Unit{ counter++ }
fun decCounter() { counter-- }

val getCounter = {    counter }

val fl = { print( "Return last exp val: " ); 100 }

val sum = { x:Int, y:Int -> x+y }

fun mirror(v: Int) : Pair<Int,Int> {
    return Pair(v, -v)
}

val f = ::sum

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

fun main() = runBlocking{
    println("BEGINS")
    for(i in 1..3) launch{ actionWithContext(i) }
    println("ENDS")
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