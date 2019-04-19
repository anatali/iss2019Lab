package state

import it.unibo.kactor.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val mymsg = MsgUtil.buildDispatch("qa", "mySysMsg", "info", "qa")

fun main() = runBlocking {
    val qa = ActorBasicFsm("qa", this, "init", {
        state( "init") {
            action { println("hello init") }
            action { scope.launch{ println("Automessage ... "); autoMsg( mymsg ) }  }
            edge("e1", targetState = "s1", cond = whenDispatch( mymsg.msgId() )  )//whenEvent( NoMsg.msgId()) doswitch()
        }
        state( "s1") {
            action { println("$name in state $stateName currentMsg=$currentMsg") }
            //edge(name="e1", targetState="s2", cond=fireIf{it.isEvent() )
        }
    }
    )

    //delay(2000)
    println("BYE")
}