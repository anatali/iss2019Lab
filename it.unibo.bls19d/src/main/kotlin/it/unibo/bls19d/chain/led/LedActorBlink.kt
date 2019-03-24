package it.unibo.bls19d.chain.led


import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.bls19d.chain.LedMsg
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


enum class States{
    INIT, ON, OFF, BLINKON, BLINKOFF
}

class LedActorBlink( name: String, val led: ILed) : ActorBasic(name) {

    var curState = States.INIT  //represent the a state with a var

    init{
    }
    override
    suspend fun actorBody( cmd : ApplMessage){
        //println( "LedActorBlink $name | RECEIVED $cmd in state=$curState" )
        /*
        Message driven pattern, BUT for each message, LedActorBlink works according to its internal state
         */
        when( curState ){
            States.INIT ->
                when( cmd.msgContent()  ){
                     LedMsg.startBlink.cmd -> {
                        led.turnOn();
                         curState = States.BLINKON;
                         autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd)
                    }
                    else -> println("LedActorBlink | msg $cmd ignored in $curState")
                }
            States.BLINKON -> {
                 when (cmd.msgContent()) {
                     LedMsg.stopBlink.cmd -> curState = States.INIT
                     LedMsg.goonBlink.cmd -> {
                         led.turnOn()
                         curState = States.BLINKOFF;
                         delay( 500 );
                         autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd )
                     }
                     else -> println("LedActorBlink | msg $cmd ignored in $curState")
                 }
            }
            States.BLINKOFF -> {
                when (cmd.msgContent()) {
                    LedMsg.stopBlink.cmd -> curState = States.INIT
                    LedMsg.goonBlink.cmd -> {
                        led.turnOff()
                        curState = States.BLINKON;
                        delay( 500 );
                        autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd )
                    }
                    else -> println("LedActorBlink | msg $cmd ignored in $curState")
                }
            }
            else -> throw Exception("LedActorBlink INCONSISTENT")
        }
      }
}


