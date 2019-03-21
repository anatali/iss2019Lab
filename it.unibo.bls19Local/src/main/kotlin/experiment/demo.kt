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
 

/*
fun main() : Unit{

    fun exec23( op:(Int,Int) -> Int ) : Int { return op(2,3) }

    val sum = { x:Int, y:Int -> x+y }
    val mul = { x:Int, y:Int -> x*y }

    println("${ exec23(sum) }")	      //5
    println("${ exec23(mul) }")	      //6



}
        */