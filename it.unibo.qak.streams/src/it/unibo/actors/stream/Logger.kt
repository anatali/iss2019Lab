package it.unibo.actors.stream

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term
import java.io.PrintWriter
import java.io.FileWriter
import java.io.ObjectOutputStream
import java.io.FileOutputStream

class Logger(name : String) : ActorStream(name){
	var pw : PrintWriter
	
 	init{   pw = PrintWriter( FileWriter(name+".txt") ) }
 
 	override suspend fun elabData(data : String ){
		//println("Loggar $name elabData $data")
		saveData( data)
	}
	
	fun saveData(   data : String )   {		
  		pw.append( "$data " )
		pw.flush()
    }

}