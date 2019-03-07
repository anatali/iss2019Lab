package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor


class AnotherBlsApplLogic :  IAppLogicFsm, BlsFsmAppl() {
    override
    fun createStateMachine() {
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
                            Messages.Stop -> {
                                state = states.HALT;
                            }
                            Messages.Click -> {
                                if (led!!.getState()) led!!.turnOff() else led!!.turnOn()
                            }
                            else -> { } //   => do nothing
                        }
                    }
                }
            }
        }
    }
}
