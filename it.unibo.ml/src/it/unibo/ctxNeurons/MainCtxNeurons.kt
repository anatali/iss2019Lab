/* Generated by AN DISI Unibo */ 
package it.unibo.ctxNeurons
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "neuurons.pl", "sysRules.pl"
	)
}
