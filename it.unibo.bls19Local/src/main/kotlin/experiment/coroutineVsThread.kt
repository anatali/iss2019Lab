package experiment
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis
import  kotlin.native.concurrent.*

fun launchTreads( n: Long ){
    val numOfJobs = n.toInt()
    var latch = CountDownLatch(numOfJobs)
    val time = measureTimeMillis {
        val c = AtomicLong(0)
        for (i in 1..n  ) {
            thread(start = true, name = "th$i") {
                c.addAndGet( i )
                //println("THREAD    |  ${Thread.currentThread().name}  c=$v")
                latch.countDown()
            }
        }
        latch.await()
        val v = c.get()
        println("THREAD    | RESULT=$v  ")
    }
    println("THREAD    | EXECUTION TIME=  $time  }")
}

suspend fun launchCoroutines( n: Long ){
    val jobs = ArrayList<Job>()
    val c    = AtomicLong()
    val time = measureTimeMillis {
        for (i in 1..n) {
            jobs += GlobalScope.launch {
                c.addAndGet(i)
            }
        }
        jobs.forEach { it.join()  }
    }
    println("COROUTINE | EXECUTION TIME=  $time c=${c.get()}.")
}

fun main( ) = runBlocking {
    showSystemInfo()
    showAboutCoroutineInfo()
    launchCoroutines( 50000L  )
    launchTreads( 50000L )
    //Utils.delay(3000)

  }