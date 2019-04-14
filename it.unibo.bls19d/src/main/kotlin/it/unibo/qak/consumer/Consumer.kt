package it.unibo.qak.consumer

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MsgUtil
import it.unibo.qak.prodCons.DataItem
import kotlinx.coroutines.CoroutineScope


class Consumer( name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {

    override suspend fun actorBody(msg: ApplMessage) {
        //println("   Consumer $name |  receives msg= $msg ")
        /*
        CONSUMER AS OBSERVABLE
         */
        val logInfo = MsgUtil.buildEvent(name, DataItem.id, "${msg.msgContent()}")
        emitLocalStreamEvent(logInfo)
    }
}
