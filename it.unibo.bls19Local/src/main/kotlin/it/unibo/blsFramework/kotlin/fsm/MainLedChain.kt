package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.utils.Utils
import it.unibo.bls19d.messages.ApplMessage
import kotlinx.coroutines.runBlocking

private fun startMsg() : ApplMessage{
     return ApplMessage("msg( start, event, main, none, start, 0 )" )
}
private fun stoptMsg() : ApplMessage{
     return ApplMessage("msg( stop, event, main, none, stop, 0 )" )
}

fun main( ) = runBlocking {

     Utils.showSystemInfo()

     val led1 : LedActor = LedActor( "led1", LedAsGui.createLed() )
     val led2 : LedActor = LedActor( "led2", LedAsGui.createLed() )
     val led3 : LedActor = LedActor( "led3", LedAsGui.createLed() )

     led1.setNextLedActor( led2.getActor() )
     led2.setNextLedActor( led3.getActor() )
     led3.setNextLedActor( led1.getActor() )

     Utils.delay(7000)

     led1.getActor().send( startMsg() )

     Utils.delay(5000)

     led1.getActor().send( stoptMsg() )
}