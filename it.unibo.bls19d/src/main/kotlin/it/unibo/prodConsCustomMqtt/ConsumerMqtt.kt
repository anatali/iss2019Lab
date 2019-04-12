package it.unibo.prodConsCustomMqtt

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.MqttUtils
import kotlinx.coroutines.CoroutineScope


class ConsumerMqtt(name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {

     init{
        mqtt.connect(name,"tcp://localhost:1883")
        mqtt.subscribe(this,"unibo/prodCons")
    }

    override suspend fun actorBody(msg: ApplMessage) {
        println("   Consumer $name |  receives msg= $msg ")
    }
}
