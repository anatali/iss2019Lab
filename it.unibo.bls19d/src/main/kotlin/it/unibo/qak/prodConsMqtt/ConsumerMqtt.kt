package it.unibo.qak.prodConsMqtt

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MqttUtils
import kotlinx.coroutines.CoroutineScope


class ConsumerMqtt(name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Consumer $name |  receives msg= $msg ")
        forward("answer", msg.msgContent(), msg.msgSender())
    }
}
