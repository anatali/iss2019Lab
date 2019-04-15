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

class Person(val name: String) {
    var age : Int = 0
    var married = false
        set( value ){
            if(age<14) println("WARNING: too joung for marriage")
            else field = true
        }
    val isAdult: Boolean
        get(){ return age >= 18} //custom getter
}

fun main(){
    val p = Person("Bob")
    p.age = 22
    p.married = true
    println( "name=${p.name}, age=${p.age}, married=${p.married} adult=${p.isAdult} ")
}