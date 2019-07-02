package it.unibo.actors.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class Source(name : String) : ActorStream(name){
 	init{	}
	
	suspend fun generate(){
		for( i in (1..10) ) {
			//println("           Source $name generates $i")	
			emitLocalStreamEvent("$i")
			delay( 300 )
		}		
	}
	
  	override suspend fun elabData(data : String ){
		generate()
	}
}