/* Generated by AN DISI Unibo */ 
package it.unibo.ctxDummyForAppl
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "otherapplhost", this, "robotmind.pl", "sysRules.pl"
	)
}

