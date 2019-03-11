package it.unibo.kactor
//FILE MsgUtil.kt

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch


enum class LedCtrlMsg{
    START, STOP, ON, OFF
}

object MsgUtil {
var count = 1;

    fun startMsg() : ApplMessage {
        count++
        return ApplMessage("msg( start, dispatch, main, none, start, $count )")
    }
    fun stoptMsg() : ApplMessage {
        count++
        return ApplMessage("msg( stop, dispatch, main, none, stop, $count )")
    }


    @kotlinx.coroutines.ObsoleteCoroutinesApi
    fun forward(msg: ApplMessage, channel: SendChannel<ApplMessage>) {
        GlobalScope.launch {
            channel.send(msg)
        }
    }
}