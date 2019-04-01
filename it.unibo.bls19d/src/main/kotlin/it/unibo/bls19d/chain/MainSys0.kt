package it.unibo.bls19d.chain
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
     sysUtil.loadInfo(
        "src/main/kotlin/it/unibo/bls19d/chain/sysDescr.pl")
    //BUILD THE SYSTEM
    sysUtil.createContexts("localhost")
}