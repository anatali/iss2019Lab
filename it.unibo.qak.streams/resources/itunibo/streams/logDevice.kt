package itunibo.streams

import it.unibo.kactor.*
import kotlinx.coroutines.CoroutineScope

class logDevice(name : String, scope: CoroutineScope) : ActorBasic( name, scope ) {
    override suspend fun actorBody(msg: ApplMessage) {
        val info   = msg.msgContent()
        val sender = msg.msgSender()
        println("   $name |  receives $info from $sender ")
    }
}