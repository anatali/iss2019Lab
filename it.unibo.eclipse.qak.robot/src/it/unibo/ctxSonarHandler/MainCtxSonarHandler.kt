/* Generated by AN DISI Unibo */ 
package it.unibo.ctxSonarHandler
import it.unibo.kactor.QakContext
import it.unibo.kactor.sysUtil
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
	QakContext.createContexts(
	        "localhost", this, "ddrworkermvc.pl", "sysRules.pl"
	)
}
