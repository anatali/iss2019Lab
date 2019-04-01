package it.unibo.kactor.test.chain
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     sysUtil.loadInfo(
        "src/main/kotlin/it/unibo/kactor/test/chain/sysDescr.pl")
    //BUILD THE SYSTEM
    sysUtil.createContexts("localhost")
}