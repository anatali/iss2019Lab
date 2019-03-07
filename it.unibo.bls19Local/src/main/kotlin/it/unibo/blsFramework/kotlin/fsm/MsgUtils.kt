package it.unibo.blsFramework.kotlin.fsm

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

enum class Messages{
    Stop,Goon,Click
}

@kotlinx.coroutines.ObsoleteCoroutinesApi
fun forward(msg : Messages, channel: SendChannel<Messages>){
    GlobalScope.launch {
        channel.send(msg)
    }
}