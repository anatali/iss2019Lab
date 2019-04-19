package state
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.*
//----------------------------------------
import alice.tuprolog.*
import it.unibo.`is`.interfaces.IOutputEnvView
import java.util.*		//Array

/*
================================================================
 QACTOR
================================================================
 */

open class QactorKotlin(val name: String,
						private val initialStateName: String,
 						buildbody: QactorKotlin.() -> Unit) {

	private var msgCount : Int = 1;
	private lateinit var currentState: State
    var currentMsg: Message = NoMsg()
	
    private val stateList     = mutableListOf<State>()
	private val msgQueueStore = mutableListOf<Message>()
	internal val msgQueue     = Channel<Message>(100) //LinkedListChannel

	private val pengine       = Prolog()

	val show : (State) -> Unit = { actionPrintln( "State ${it.name} currentMsg=$currentMsg" )}


	init{
		println("QActor $name | STARTS ")
		buildbody()			//Build the structural part
		initialize()
	}

	private fun initialize() {
		currentState = getStateByName(initialStateName)
		println("QActor $name |  initialize currentState=${currentState.name}")
	}

	//================================== STRUCTURAL =======================================
	fun state(name: String, build: State.() -> Unit) {
		val state = State(name)
		state.build()
		stateList.add(state)
	}

	private fun getStateByName(name: String): State {
		return stateList.firstOrNull { it.name == name}
				?: throw NoSuchElementException(name)
	}
	//===========================================================================================


	//Called by startTheActors in Context

	fun work() {
		println("QActor $name | work in STATE ${currentState.name}")
		GlobalScope.launch {
			do{
				currentState.enterState()
				println("QActor $name | TRANSITION in STATE ${currentState.name}")
				//EMPTY MOVE
				val nextState = checkTransition( NoMsg() )
				if( nextState is State  ) currentState = nextState
				else currentState = transition()
			}while( true )
		}
	}

	private suspend fun lookAtMsgQueue() : Pair<Message, State>{
		var nextState : State?
		do {
			val msg = msgQueue.receive()
			//println("QActor $name | lookAtMsgQueue received  $msg")
			nextState = checkTransition(msg)
			if( nextState is State){
 				return Pair(msg,nextState)
			}else{ //EXCLUDE EVENTS FROM msgQueueStore
				if( ! (msg is Message.Event) ) {
					msgQueueStore.add(msg)
					println("QActor $name | lookAtMsgQueue added $msg in msgQueueStore")
				}
				else{ println("QActor $name | DISCARDING THE EVENT: $msg")}
			}
		}while( true )
	}

	private fun checkTransition(msg: Message) : State? {
	 	val edge = currentState.getEdgeForMessage(msg)
		//println("QActor $name | checkTransition ENTRY $msg , currentState=${currentState.name} edge=${edge is Edge}")
		return if(edge is Edge) {
			edge.enterEdge {  getStateByName(it)  }
		}else{
			//println("QActor $name | checkTransition NO next State for $msg !!!")
			null
		}
	}
	//Returns the next state by looking at th messages so far received or waits on infochannel
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	suspend fun transition() : State {
		println("QActor $name | transition in:${currentState.name}")
		val state = lookAtMsgQueueStore()
		if( state is State){
			println("QActor $name | transition state from msgQueueStore:${state.name}")
			return state
		}
 		println("QActor $name | WAITS in:${currentState.name}")
		val (msg,newstate) = lookAtMsgQueue() //infochannel.receive()
		//println("QActor $name | RESUMES to:${newstate.name} from:${currentState.name} since:$msg")
		currentMsg =  msg
		return newstate
	}

	private fun lookAtMsgQueueStore() :  State?{
		println("QActor $name | lookAtMsgQueueStore :${msgQueueStore.size}")
		msgQueueStore.forEach{
			val state = checkTransition( it )
			if( state is State){
				msgQueueStore.remove( it )
				return state
			} 
		}
		return null
	}

	fun isEvent( ev: Message, evName: String, v:String ) : Boolean{
		return  ev is Message.Event && ev.name == evName && ev.body==v
	}
 	fun fireIf( cond: (Message) -> Boolean ) : Edge.() -> Unit{
  		return { eventHandler = cond }
	}
    fun whenEvent( evName: String, evVal: String ) : Edge.() -> Unit{
        return { eventHandler = { it is Message.Event && it.name == evName && it.body==evVal} }
    }
	fun whenEvent( evName: String, cond: (Message)->Boolean ) : Edge.() -> Unit{
		return { eventHandler = {
			//println("YYY ${it} }"); //just to show when it happens
			it is Message.Event && it.name == evName && cond(it) }}
	}
	fun whenEvent( evName: String ) : Edge.() -> Unit{
		return { eventHandler = { it is Message.Event && it.name == evName } }
	}
    fun whenDispatch( msgName: String ) : Edge.() -> Unit{
        return { eventHandler = { it is Message.Dispatch && it.name == msgName } }
    }
    fun whenRequest( reqName: String ) : Edge.() -> Unit{
        return { eventHandler = { it is Message.Request && it.name == reqName } }
    }
	fun doswitch() : Edge.() -> Unit{   //TODO

   		return {   eventHandler = { println("doswitchhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");true} }
	}  
	
  	/*
	Send the given Message.Eeent to all the actors in all the contexts
	 */
	/*
         -----------------------------------------------
             BUILT-IN ACTIONS
         -----------------------------------------------
    */
	fun showmsgQueueStore(){
		if( msgQueueStore.size == 0 )
			println("			&&& msgQueueStore ($name) is empty")
 		else msgQueueStore.forEach{
			println("		&&& msgQueueStore ($name) item: $it")
		}
	}
	fun actionPrintln(msg : String){ println("			${this.name} | ACTION:  $msg") }

}

fun main() = runBlocking{
val qa = QactorKotlin("qa", "init", {
	state(name="init"){
		action( show )
		action{ println("hello init")}
		edge(name="e1", targetState="s1", cond=doswitch() )
	}
	state(name="s1"){
		action( show )
		action{ println("hello s1")}
		//edge(name="e1", targetState="s1", cond={ true } )
	}
})
	qa.work() //start the actor
	delay(2000)
}