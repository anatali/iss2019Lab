package it.unibo.kactor
import alice.tuprolog.*
import alice.tuprolog.Term.createTerm
import java.io.FileInputStream


/*
====================================================================
Package level functions
====================================================================
 */

fun solve( goal: String, pengine : Prolog, resVar: String  ) : String? {
	//println("StateMachine $name | solveGoal ${goal}" );
	val sol = pengine.solve( "$goal.")
	if( sol is SolveInfo ) {
		val result = sol.getVarValue(resVar)  //Term
		println( "sol=$result" )
		return  sol.getVarValue(resVar).toString()
	}
	else return null
}
fun solveT( goal: String, pengine : Prolog, resVar: String  ) : Term? {
	//println("StateMachine $name | solveGoal ${goal}" );
	val sol = pengine.solve( "$goal.")
	if( sol is SolveInfo ) {
		if( resVar.isNotEmpty()   ){
			val result = sol.getVarValue(resVar)  //Term
			println( "sol=$result" )
			return  result
		}else{
 			return createTerm("ok")
		}
	}
	else return null
}

fun loadTheory(path: String,  pengine: Prolog) {
	try {
		val worldTh = Theory(FileInputStream(path))
		pengine.addTheory(worldTh)
	} catch (e: Exception) {
		println("loadheory   |   WARNING: ${e}" );
	}
}



