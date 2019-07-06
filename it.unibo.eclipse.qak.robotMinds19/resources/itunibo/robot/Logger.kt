package itunibo.robot

 
import it.unibo.kactor.ApplMessage
import alice.tuprolog.Struct
import alice.tuprolog.Term
import java.io.PrintWriter
import java.io.FileWriter
import java.io.ObjectOutputStream
import java.io.FileOutputStream

class Logger(name : String) : ApplActorDataStream(name){
	var pw : PrintWriter
	
 	init{   pw = PrintWriter( FileWriter(name+".txt") ) }
 
 	override protected suspend fun elabData(data : String ){
		println("---------------------------------------------------- Logger $name elabData $data")
		saveData( data)
	}
	
	fun saveData(   data : String )   {		
  		pw.append( "$data\n " )
		pw.flush()
    }

}