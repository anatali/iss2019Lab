/* Generated by AN DISI Unibo */ 
package it.unibo.ctxTimeTest
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "timetest.pl", "sysRules.pl"
	)
}

