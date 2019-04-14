package it.unibo.qak.producer

import it.unibo.kactor.MsgUtil
import it.unibo.qak.consumer.Consumer
import it.unibo.qak.logger.logDevice
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("START")
    val producer = Producer("producer", this)
    val consumer1 = Consumer("consumer1", this)
    val consumer2 = Consumer("consumer2", this)
    val logger    = logDevice("logger", this)

    producer.subscribe(consumer1).subscribe(logger)
    producer.subscribe(consumer2).subscribe(logger)

    MsgUtil.sendMsg("local_start", "10", producer )
    println("END")
}




