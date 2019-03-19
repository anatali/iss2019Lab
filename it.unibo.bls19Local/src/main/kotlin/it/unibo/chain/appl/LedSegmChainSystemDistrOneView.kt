package it.unibo.chain.appl

import it.unibo.bls.utils.Utils
import it.unibo.chain.segment7.LedSegment
import it.unibo.chain.segment7.LedSegmHorizontal
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame
import it.unibo.kactor.MsgUtil

/*
    The system is a distributed chain of LedSegment, each controlled by a LedInChainCtrlActor
    Each LedInChainCtrlActor has a reference to the next (the last to the first)
    Each LedSegment is included in a JFrame with a GridLayout
 */
class LedSegmChainSystemDistrOneView(val name:String, val numOfElements:Int  ){
    val segmList  : MutableList<LedSegment>          = mutableListOf<LedSegment>()
    val actorList : ArrayList<LedInChainCtrlActor>   = arrayListOf<LedInChainCtrlActor>()

    init{
        JFrame.setDefaultLookAndFeelDecorated(true)
        val frame = JFrame("Chain")
        initViewContext( frame )
        createComponents( frame )
        connecctComponents()
        frame.isVisible = true
    }

    fun initViewContext( frame: JFrame ){
        frame.setSize(numOfElements*120, 80)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.layout = GridLayout(1, numOfElements)
        frame.contentPane.background = Color.BLUE
    }

    fun createComponents( frame: JFrame){
        for( i in 1..numOfElements ){
            val segm = LedSegmHorizontal("segm$i", 110, 80)
            segmList.add(segm)
            frame.add(segm)
            actorList.add(LedInChainCtrlActor("led$i", segm))
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
        //actorList.get(0).getChannel().send( MsgUtil.startMsg())
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
    val system = LedSegmChainSystemDistrOneView("ChainDistributed", 3)
    system.startTheSystem()
    Utils.delay(5000)
    system.stopTheSystem()
}