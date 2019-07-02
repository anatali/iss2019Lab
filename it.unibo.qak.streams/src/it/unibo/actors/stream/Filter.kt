package it.unibo.actors.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term

class Filter(name : String) : ActorStream(name){
 	init{}
	
 	override suspend fun elabData(data : String ){
		val n = Integer.parseInt(data)
		println("Filter $name input= $n")
		if( n % 2 == 0 ){
			emitLocalStreamEvent( data )
		}
	}
}