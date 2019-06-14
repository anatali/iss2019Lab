package itunibo.streams

import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import kotlinx.coroutines.launch

object sonarStreamPipe {
	  
	fun create( actor: ActorBasic ){
		println("sonarStreamPipe CREATE ")
		val filter =  sonardatafilter("filter", actor )
		sonarHCSR04SupportAsStream.create(actor, filter)	            //stream source with first subscriber (filter)
		filter.subscribe( obstacleDetector("obsDetector", actor )  )	//first part of the pipe
	}

}