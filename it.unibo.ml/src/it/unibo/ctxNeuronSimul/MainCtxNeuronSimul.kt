/* Generated by AN DISI Unibo */ 
package it.unibo.ctxNeuronSimul
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "neuronsimul.pl", "sysRules.pl"
	)
}
