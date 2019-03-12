package it.unibo.chain.appl

import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.bls.utils.Utils
import it.unibo.kactor.ApplMessage
import it.unibo.chain.segment7.LedSegmVerticalRight
import it.unibo.chain.segment7.LedSegment
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.ActorBasic

/*
The system:
    ia an ActorBasic that can handle 'start' and 'stop' messages
    creates numOfElements leds and inserts the reference to each led into a MutableList
        each led is a LedSegment (LedSegmVerticalRight) JPanel inserted in the given JFrame
        the given JFrame is made visible in order to provide a 'global view' of the leds in the system
    controls the leds with the applLogic function, that is activated by the message 'start'
        initially sent from outside
        afterwards sent form the system itself, if it is not stopped
 */
class LedSegmChainSystemMonolitic(
     name:String, val numOfElements:Int, var  frame : JFrame? ) : ActorBasic(name) {

    private val TIMEON     = 250
    private val numOfLeds  = numOfElements;
    private var stopped    = false
    private val segmList : MutableList<LedSegment> = mutableListOf<LedSegment>()

       init{
        if( frame is JFrame ){
            initWithFrame( frame!! )
            createComponents( frame!! )
            frame!!.isVisible = true
        }
      }
      fun initWithFrame( f: JFrame ){
        f.setSize(120*numOfElements, 200)
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.layout = GridLayout(1, numOfLeds)
        f.contentPane.background = Color.BLUE
    }

    fun createComponents( f: JFrame ){
        for( i in 1..numOfLeds ){
            val segm = LedSegmVerticalRight("segm$i", 110, 180)
            segmList.add(segm)
            f.add(segm)
        }
    }

    override
    suspend fun actorBody( msg : ApplMessage){
        if( stopped ) println("LedSegmChainSystemMonolitic | is stopped")
        else
            when( msg.msgId() ){
                "start" ->  {  doStep() }
                "stop"  ->   stopped = true
            }
    }

    suspend fun doStep(  ){
        segmList.forEach(   ){
            it.turnOn()
            Utils.delay(TIMEON)
            it.turnOff()
        }
        autoMsg( MsgUtil.startMsg() )
    }

    suspend fun startTheSystem(){
        autoMsg( MsgUtil.startMsg() )
    }

    suspend fun stopTheSystem(){
        println("============================================ STOP")
        autoMsg( MsgUtil.stoptMsg() )
    }

}//LedSegmChainSystemMonolitic

/*
==================================================0
MAIN
==================================================0
*/
fun main( ) = runBlocking {
    Utils.showSystemInfo()
//A frame for the LedGui segments
    JFrame.setDefaultLookAndFeelDecorated(true)
    val frame = JFrame("Chain")
//Business Logic
        val sys = LedSegmChainSystemMonolitic("Chain", 5, frame)
    sys.startTheSystem()
    Utils.delay(5000)
    sys.stopTheSystem()

}