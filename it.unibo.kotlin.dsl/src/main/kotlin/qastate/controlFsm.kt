package qastate

import it.unibo.kactor.ActorBasicFsm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/*
class controlFsm(name:String, scope: CoroutineScope  ) :
    ActorBasicFsm( name, scope, "init", {

    state( "init") {
        action {
            println("hello init")
            scope.launch {
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

*/


