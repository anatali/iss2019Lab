package qastate

import it.unibo.kactor.ActorBasicFsm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//val concreteLed = LedSegm()

class ledFsm(name:String, scope: CoroutineScope  ) :  ActorBasicFsm( name, scope) {

    lateinit var concreteLed : LedSegm

    override fun  getInitialState() : String { return "init" }

    override fun  getBody() : (ActorBasicFsm.() -> Unit) {
        return{
            state( "init") {
                action {
                    concreteLed = LedSegm()
                    //println("concreteLed = $concreteLed")
                    concreteLed.turnOn()
                }
                transition("e1", targetState = "s1", cond = whenEvent("alarm")   )
            }
            state( "s1") {
                action {
                    println("$name in state $stateName currentMsg=$currentMsg")
                }
                //transition("e1", targetState = "s2", cond = doswitch()  )
            }
        }
    }
    //init{ setBody( getBody(), getInitialState() ) }

}




