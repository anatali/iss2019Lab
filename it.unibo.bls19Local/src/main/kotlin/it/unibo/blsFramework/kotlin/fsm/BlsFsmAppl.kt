package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.utils.Utils
import it.unibo.blsFramework.interfaces.ILedModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext


enum class states{
    WORK, HALT
}

open class BlsFsmAppl : IAppLogicFsm {
    protected var led: ILedModel? = null
    protected var numCalls = 0

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    protected var fsm : SendChannel<Messages>? = null

    @kotlinx.coroutines.ObsoleteCoroutinesApi
    protected val dispatcher = newFixedThreadPoolContext(2, "mypool")


    init{
        createStateMachine()
    }
    override fun setControlled(led: ILedModel) {
        this.led = led
    }
    /*
    FINITE STATE MACHINE (PROACTIVE PART)
     */
    @kotlinx.coroutines.ObsoleteCoroutinesApi
    open fun createStateMachine() {
        fsm = GlobalScope.actor<Messages>(dispatcher, 1) {
            var state = states.HALT

            for (msg in channel) {
                println("   ACTOR stateMachine |  msg= $msg state= $state ")
                when (state) {
                    states.HALT -> {
                        when (msg) {
                            Messages.Click -> {
                                state = states.WORK
                                forward( Messages.Goon, fsm!!)     //auto mesg
                            }
                            else -> { } //Stop,Goon => do nothing
                        }
                    }
                    states.WORK -> {
                        when (msg) {
                            Messages.Goon -> {
                                if (led!!.getState()) led!!.turnOff() else led!!.turnOn()
                                Utils.delay(250)
                                forward( Messages.Goon, fsm!!)      //auto mesg
                            }
                            Messages.Stop -> {
                                state = states.HALT;
                            }
                            Messages.Click -> {
                                state = states.HALT;
                            }
                            else -> { } //??  => do nothing
                        }
                    }
                }
            }
        }
    }


    @kotlinx.coroutines.ObsoleteCoroutinesApi
    override fun getStateMachine( ) :  SendChannel<Messages>{
        println("   BlsFsmAppl | getStateMachine")
        return fsm!!
    }
    //Useful for testing
     override fun getNumOfCalls(): Int {
        return numCalls
    }
}
