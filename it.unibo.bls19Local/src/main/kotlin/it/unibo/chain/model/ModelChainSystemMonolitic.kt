package it.unibo.chain.model

import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.interfaces.ILedObserver
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.chain.segment7.LedSegment
import kotlinx.coroutines.runBlocking
import it.unibo.kactor.MsgUtil
import it.unibo.chain.segment7.LedSegmHorizontal

/*
The system:
    ia an ActorBasic that can handle 'start' and 'stop' messages
 */
class ModelChainSystemMonolitic(
     name:String, val numOfLeds:Int, var  frame : JFrame? ) : ActorBasic(name) {

    private val dt         = 500
    private var stopped    = false
    private val segmList   : MutableList<LedSegment>   = mutableListOf<LedSegment>()
    private val ledObsList : MutableList<ILedObserver> = mutableListOf<ILedObserver >()
    private  lateinit  var  chain : ChainModel

       init{
        if( frame is JFrame ){
            initWithFrame( frame!! )
            createComponents( frame!! )
            frame!!.isVisible = true
        }
      }
      fun initWithFrame( f: JFrame ){
        f.setSize(120*numOfLeds, 180)
        f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        f.layout = GridLayout(1, numOfLeds)
        f.contentPane.background = Color.BLUE
    }

    fun createComponents( frame: JFrame ){
        for( i in 0..numOfLeds-1 ){
            val ledObs = LedInChainObs("ledObs$i")
            ledObsList.add( ledObs )
            //val segm   = LedSegmVerticalRight("segm$i", 110, 180)
            val segm   = LedSegmHorizontal("segm$i", 110, 180)
            segmList.add(segm)
            ledObs.setLed(segm)
            frame.add(segm)
        }
        chain = ChainModel( ledObsList )
    }

    override
    suspend fun actorBody( msg : ApplMessage){
        if( stopped ) println("ModelChainSystemMonolitic | is stopped")
        else
            when( msg.msgId() ){
                "start" ->   work()         //STATE WORK
                "stop"  ->   {              //STATE HALT
                    stopped = true
                    turnOnAll()
                }
            }
    }

    suspend fun work(  ){
         for( i in 0..numOfLeds-1 ){
            chain.turnOn(i)
            Utils.delay(dt)
            chain.turnOff(i)
        }
        autoMsg( MsgUtil.startMsg() )
    }

    suspend fun turnOnAll(){
        for( i in 0..numOfLeds-1 ){
            chain.turnOn(i)
        }
    }

    suspend fun startTheSystem(){
        autoMsg( MsgUtil.startMsg() )
    }

    suspend fun stopTheSystem(){
        println("============================================ STOP")
        autoMsg( MsgUtil.stoptMsg() )
    }

}//ModelChainSystemMonolitic

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
    val sys = ModelChainSystemMonolitic("Chain", 3, frame)
    sys.startTheSystem()
    Utils.delay(5000)
    sys.stopTheSystem()

}