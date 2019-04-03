package it.unibo.kactor.test

import it.unibo.kactor.*
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     sysUtil.createContexts("localhost",
        "src/main/kotlin/it/unibo/kactor/test/sysDescr.pl",
        "src/main/kotlin/it/unibo/kactor/test/sysRules.pl" )

    MsgUtil.sendMsg( "start", "start", sysUtil.getActor("producer")!! )

}