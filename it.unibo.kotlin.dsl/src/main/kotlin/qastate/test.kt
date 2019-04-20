package qastate

import it.unibo.kactor.QakContext
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("START   ")
    QakContext.createContexts(
        "localhost", this,
        "sysDescr.pl", "sysRules.pl"
    )

}