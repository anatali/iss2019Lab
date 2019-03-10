package it.unibo.blsFramework.kotlin.chain.segment7

import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.kotlin.chain.ApplMessage
import kotlinx.coroutines.runBlocking
import java.awt.Color
import java.awt.GridLayout
import javax.swing.JFrame

private fun startMsg() : ApplMessage {
     return ApplMessage("msg( start, event, main, none, start, 0 )")
}
private fun stoptMsg() : ApplMessage {
     return ApplMessage("msg( stop, event, main, none, stop, 0 )")
}

     fun main( ) = runBlocking{  //

          Utils.showSystemInfo()
           JFrame.setDefaultLookAndFeelDecorated(true)
          val f = JFrame("Chain")
          f.setSize(500, 200)
          f.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
          f.layout = GridLayout(1, 3)

          f.contentPane.background = Color.BLUE

          val logic = ChainLogic("Chain", f)

          logic.getActor().send( logic.startMsg() )
          Utils.delay(5000)
          logic.getActor().send( logic.stoptMsg() )
      
     }
