/* Generated by AN DISI Unibo */ 
package it.unibo.ctxLeds2
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "192.168.1.18", this, "chain.pl", "sysRules.pl"
	)
}

