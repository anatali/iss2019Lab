package it.unibo.bls19d.kactors.led


import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.interfaces.ILed
import it.unibo.bls.utils.Utils
import it.unibo.bls19d.kactors.LedMsg
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


enum class States{
    INIT, ON, OFF, BLINKON, BLINKOFF
}

class LedActor( name: String, val led: ILed) : ActorBasic(name) {

    var curState = States.INIT  //represent the a state with a var
    //var ledgui : ILed? = null

    init{
        /*
        val frame    = Utils.initFrame(200, 200)
        ledgui       = LedSegmHorizontal("secs0", 110, 180)
        frame.add( ledgui as JPanel)
        frame.validate()
        ledgui!!.turnOff()
        */
    }
    override
    suspend fun actorBody( cmd : ApplMessage){
        //println( "LedActor | RECEIVED $cmd" )
        /*
        Message driven pattern, BUT for each message, LedActor works according to its internal state
         */
        when( curState ){
            States.INIT ->
                when( cmd.msgContent()  ){
                     LedMsg.startBlink.cmd -> {
                        led.turnOn(); curState =
                             States.BLINKON; autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd)
                    }
                    else -> println("LedActor | msg $cmd ignored in $curState")
                }
            States.BLINKON -> {
                 when (cmd.msgContent()) {
                     LedMsg.stopBlink.cmd -> curState = States.INIT
                     LedMsg.goonBlink.cmd -> {led.turnOn()
                         curState =
                             States.BLINKOFF; delay( 500 ); autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd )
                     }
                     else -> println("LedActor | msg $cmd ignored in $curState")
                 }
            }
            States.BLINKOFF -> {
                when (cmd.msgContent()) {
                    LedMsg.stopBlink.cmd -> curState = States.INIT
                    LedMsg.goonBlink.cmd -> {led.turnOff()
                        curState =
                            States.BLINKON; delay( 500 ); autoMsg( LedMsg.goonBlink.name, LedMsg.goonBlink.cmd )
                    }
                    else -> println("LedActor | msg $cmd ignored in $curState")
                }
            }
            else -> throw Exception("LedActor INCONSISTENT")
        }

      }



}

/* Rapid check */
fun main() : Unit = runBlocking{
    val led1 = LedActor("led1", LedAsGui.createLed(Utils.initFrame(200, 200)))
    //MsgUtil.forward( LedMsg.startBlink.name, LedMsg.startBlink.cmd, led1 )
    delay(2000)
    //MsgUtil.forward( LedMsg.stopBlink.name, LedMsg.stopBlink.cmd, led1 )
    /*
      for( i in 1..3 ) {
        delay(500)
        led1.forward("turnOn", led1)
        delay(500)
        led1.forward("turnOff", led1)
    }
    */
}
