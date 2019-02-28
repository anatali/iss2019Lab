package experiment
import kotlinx.coroutines.*

fun showSystemInfo(){
    println("AT START | num of threads=${Thread.activeCount()} current thread= ${Thread.currentThread().name}")
}

suspend fun showAboutCoroutineInfo(){
    val task = GlobalScope.launch{
        println("FOR COROUTINE | num of threads=${Thread.activeCount()} current thread= ${Thread.currentThread().name}")
    }
    task.join()
}