/* Generated by AN DISI Unibo */ 
package it.unibo.ctxLedsOnRasp
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "192.168.1.22", this, "chain.pl", "sysRules.pl"
	)
}
