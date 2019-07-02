package it.unibo.actors.stream

import kotlinx.coroutines.runBlocking
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay

//MainStreamTest{
	
	fun main() = runBlocking {
		val source         = Source("dataSource")
		val filter         = Filter("dataFilter")
		val loggerFiltered = Logger("loggerFiltered")
		val loggerAll      = Logger("loggerAll")
/*
	 source 
			--> filter --> loggerFiltered
			--> loggerAll
*/		
		source.subscribe(filter).subscribe(loggerFiltered)
		source.subscribe(loggerAll)
		
		//val startMsg = MsgUtil.buildDispatch("main","start", "start", "source")
		MsgUtil.sendMsg("start", "start(ok)", source)
		
		delay( 10000 )
		println("main ENDS") 
	}
 