package it.unibo.blsFramework.kotlin.chain.segment7

import it.unibo.blsFramework.kotlin.chain.ApplMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.newFixedThreadPoolContext
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.channels.SendChannel

class ChainLogic( val name:String, var  frame : JFrame? ){
    private val dispatcher = newFixedThreadPoolContext(2, "mypool")
    private val numOfLeds = 3;
    private var stopped    = false
    private val segmList : MutableList<LedSegment> = mutableListOf<LedSegment>()
    private var count = 0;

       init{
        if( frame is JFrame ){
             initWithFrame( frame!! )
        }
      }
      fun initWithFrame( f: JFrame ){
        f.setSize(500, 200)
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.layout = GridLayout(1, numOfLeds)
        f.contentPane.background = Color.BLUE
           for( i in 1..numOfLeds ){
              val segm = LedSegmVerticalRight("segm$i", 110, 180)
              segmList.add(segm)
              f.add(segm)
          }
          f.isVisible = true
    }
/*

 */
    public fun startMsg() : ApplMessage {
        count++
        return ApplMessage("msg( start, event, main, none, start, $count )")
    }
    public fun stoptMsg() : ApplMessage {
        count++
        return ApplMessage("msg( stop, event, main, none, stop, $count )")
    }

@kotlinx.coroutines.ObsoleteCoroutinesApi
val actorCtrl = GlobalScope.actor<ApplMessage>(dispatcher, 3 ) {
    for( msg in channel ) {
        println("   ACTOR $name |  msg= $msg "  )
        if( stopped ){
            println("ChainLogic | alreay stopped")
        }
        else
            when( msg.msgId() ){
                "start" ->  {  applLogic() }
                "stop"  ->   stopped = true
            }
    }
}

    suspend fun applLogic(){
        if( ! stopped ){
            segmList.forEach(   ){
                it.turnOn()
                Utils.delay(500)
                it.turnOff()
            }
            actorCtrl.send( startMsg() )        //AUTOMSG
        }else{
            println("ChainLogic | stopped")
        }
    }

    fun getActor() : SendChannel<ApplMessage> {
        return actorCtrl
    }

}