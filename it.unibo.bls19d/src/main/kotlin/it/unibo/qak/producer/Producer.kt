package it.unibo.qak.producer

import it.unibo.kactor.*
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

val fiboSeq = sequence{
    var a = 0
    var b = 1
    yield(1)   //first
    while (true) {
        val tmp = a + b
        println("   fiboSeq |  called with ${tmp} ")
        yield(tmp)   //next
        a = b
        b = tmp
    }
}

class Producer( name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {
var n = 1
    override suspend fun actorBody(msg: ApplMessage) {
        //println("   Producer $name |  receives msg= $msg ")
        when ( msg.msgId() ) {
            "start" -> {
                //println("   Producer $name |  receives msg= $msg ")
                val v = fiboSeq.elementAt(n++)      //RECOMPUTES!!!
                println("   Producer $name |  n=${n-1} fibo v= $v ")
                val d = DataItem( "data(fibo($n,${v}))")
                //forward("data", "item$i", "consumer")
                emit(d.id, d.item)
            }
            else -> println("   Producer $name |  msg= $msg ")
        }
    }
}
