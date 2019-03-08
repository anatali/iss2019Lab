package it.unibo.blsFramework.kotlin.fsm

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

//FILE MsgUtils
enum class Messages{
    Stop,Goon,Click
}

enum class LedCtrlMsg{
    START, STOP, ON, OFF
}
@kotlinx.coroutines.ObsoleteCoroutinesApi
fun forward(msg : Messages, channel: SendChannel<Messages>){
    GlobalScope.launch {
        channel.send(msg)
    }
}