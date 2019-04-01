package it.unibo.bls19d.chain.led

import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

open class LedActorAlone( name: String, val led: ILed) : ActorBasic(name) {

    override suspend fun actorBody( cmd : ApplMessage){
        println( "LedActor $name | RECEIVED $cmd " )
        when( cmd.msgContent()  ){
            "on" -> {
                led.turnOn();
            }
            "off" -> {
                led.turnOff();
            }
            else ->
            println("LedActor does not handle ${cmd.msgContent()} ")
            //throw Exception("LedActor does not handle ${cmd.msgContent()} ")
        }
      }
}

/* Rapid check */
fun main() : Unit = runBlocking{
    val led      = LedAsGui.createLed(Utils.initFrame(200, 200))
    led.turnOff()
    val ledActor = LedActorAlone("ledActor", led )
    //MsgUtil.forward( LedMsg.startBlink.name, LedMsg.startBlink.cmd, led1 )
    delay(2000)
   //CONTROL
      for( i in 1..3 ) {
        delay(500)
        ledActor.forward("ledCmd", "on", ledActor)
        delay(500)
        ledActor.forward("ledCmd", "off", ledActor)
    }

}
