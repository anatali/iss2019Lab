package it.unibo.chain.appl

import it.unibo.bls.utils.Utils
import it.unibo.kactor.MsgUtil
import it.unibo.chain.segment7.LedSegment
import it.unibo.chain.segment7.LedSegmHorizontal
import it.unibo.chain.segment7.LedSegmVerticalLeft
import it.unibo.chain.segment7.LedSegmVerticalRight
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

/*
    The system is a distributed chain of LedSegment, each controlled by a LedInChainCtrlActor
    Each LedInChainCtrlActor has a reference to the next (the last to the first)
    Each LedSegment is included in an ad-hoc JFrame
 */

class LedSegmChainSystemDistr(val name:String, val numOfElements:Int  ){
    val segmList  : MutableList<LedSegment>   = mutableListOf<LedSegment>()
    val actorList : ArrayList<LedInChainCtrlActor>   = arrayListOf<LedInChainCtrlActor>()

    init{
        createComponents(   )
        connecctComponents()
    }

    fun  createFrame(i:Int) : JFrame{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        frame.setSize( 120, 150)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, 1)
        frame.setLocation(100+i*120 ,100)
        frame.contentPane.background = Color.BLUE
         return frame
    }

    fun createComponents(  ){
        for( i in 1..numOfElements ){
            val  frame = createFrame(i)
            //val segm = LedSegmHorizontal("segm$i", 110, 180)
            val segm = LedSegmVerticalRight("segm$i", 110, 180)
            segmList.add(segm)
            frame.add(segm)
            frame.isVisible = true
            actorList.add(LedInChainCtrlActor("led1", segm))
        }
    }

    fun connecctComponents(){
        for( i in 0..actorList.size-2){
            actorList.get(i).setNextLedActor(actorList.get(i+1).getChannel())
        }
        actorList.get(actorList.size-1).setNextLedActor(actorList.get(0).getChannel())
    }


    suspend fun startTheSystem(){
        MsgUtil.forward( MsgUtil.startMsg(), actorList.get(0).getChannel()  )
            //actorList.get(0).getActor().send( startMsg())
    }

    suspend fun stopTheSystem(){
        println("============================================ STOP")
        MsgUtil.forward( MsgUtil.stoptMsg(), actorList.get(0).getChannel()  )
        //actorList.get(0).getChannel().send( MsgUtil.stoptMsg())
    }
}

/*
==================================================0
MAIN
==================================================0
*/
fun main( ) = runBlocking {
    Utils.showSystemInfo()
    val system = LedSegmChainSystemDistr("ChainDistributed", 3)
    system.startTheSystem()
    Utils.delay(5000)
    system.stopTheSystem()
}
