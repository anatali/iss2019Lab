package it.unibo.kactor.test

import it.unibo.kactor.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     QakContext.createContexts("localhost",this,
        "src/main/kotlin/it/unibo/kactor/test/sysDescr.pl",
        "src/main/kotlin/it/unibo/kactor/test/sysRules.pl" )

    val producer = sysUtil.getActor("producer")
    if( producer is Producer )
        MsgUtil.sendMsg( "start", "start", producer )


}