package state

import it.unibo.kactor.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



fun main() = runBlocking {
    val qa = Qak1("qa", this)
}
/*
fun main() = runBlocking {
    val qa = ActorBasicFsm("qa", this, "init", {
        state( "init") {
             action {
                 println("hello init")
                 launch {
                     delay(1000)
                     println("$name in state $stateName AUTO SEND")
                     autoMsg(mymsg)
                 }
             }
            transition("e1", targetState = "s1", cond = doswitch()   )
        }
        state( "s1") {
            action { println("$name in state $stateName currentMsg=$currentMsg") }
            transition("e1", targetState = "s2", cond = doswitch()  )
        }
        state( "s2") {
            action { println("$name in state $stateName currentMsg=$currentMsg") }
            transition("e2", targetState = "s3", cond = whenDispatch( mymsg.msgId() )  )
        }
        state( "s3") {
            action { println("$name in state $stateName currentMsg=$currentMsg") }
            //transition(name="e3", targetState="s2", cond=fireIf{it.isEvent() )
        }
    }
    )

    val qb = ActorBasicFsm("qb", this, "init", {
        state( "init") {
            action {
                launch {
                    delay( 500 )
                    println("$name in state $stateName SEND")
                    forward( mymsg.msgId(),"hello from qb","qa")
                    delay( 500 )
                    println("$name in state $stateName EMIT")
                    emit( "alarm", "fire")
                }
            }
            transition("e1", targetState = "s1", cond = whenEvent("alarm")   )
        }
        state( "s1") {
            action { println("$name in state $stateName currentMsg=$currentMsg") }
            //transition("e1", targetState = "s2", cond = doswitch()  )
        }

    }
    )


}
        */