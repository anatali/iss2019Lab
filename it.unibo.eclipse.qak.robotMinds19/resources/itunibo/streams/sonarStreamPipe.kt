package itunibo.streams

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasicFsm

object sonarStreamPipe {
	  
	fun create( actor: ActorBasicFsm ){
		println("sonarStreamPipe CREATE ")
		val filter =  sonardatafilter("filter", actor )
		sonarHCSR04SupportAsStream.create(actor, filter)	//stream source with first subscriber (filter)
	}

}