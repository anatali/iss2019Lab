package it.unibo.kactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.util.NoSuchElementException

/*
================================================================
 STATE
================================================================
 */
class State(val stateName : String) {
    private val edgeList          = mutableListOf<Edge>()
    private val stateEnterAction  = mutableListOf<(State) -> Unit>()

    fun edge(edgeName: String, targetState: String, cond: Edge.() -> Unit) {
        val edge = Edge(edgeName, targetState)
        //println("                     edge $name $targetState")
        edge.cond() //set eventHandler (given by user) See fireIf
        edgeList.add(edge)
    }
    //Add an action which will be called when the state is entered
    fun action( a: (State) -> Unit) {
        //println("State $stateName    | add action  $a")
        stateEnterAction.add(a)
    }
    operator fun plus (action: (State) -> Unit) {
        stateEnterAction.add(action)
    }
    fun addAction (action: (State) -> Unit) {
        stateEnterAction.add(action)
    }
    fun enterState() {
        stateEnterAction.forEach {  it(this)  }
    }

    //Get the appropriate Edge for the Message
    fun getEdgeForMessage(msg: ApplMessage): Edge? {
        //println("State $name       | getEdgeForMessage  $msg  list=${edgeList.size} ")
        val first = edgeList.firstOrNull { it.canHandleMessage(msg) }
        return first
    }
}

/*
================================================================
 EDGE
================================================================
 */
class Edge(val edgeName: String, val targetState: String) {

    lateinit var edgeEventHandler: (ApplMessage) -> Boolean
    private val actionList = mutableListOf<(Edge) -> Unit>()

    fun action(action: (Edge) -> Unit) { //MEALY?
        //println("Edge  | add ACTION:  $action")
        actionList.add(action)
    }

    //Invoke when you go down the edge to another state
    fun enterEdge(retrieveState: (String) -> State): State {
        //println("Edge  | enterEdge  retrieveState: ${retrieveState} actionList=$actionList")
        actionList.forEach { it(this) }         //MEALY?
        return retrieveState(targetState)
    }

    fun canHandleMessage(msg: ApplMessage): Boolean {
        //println("Edge  | canHandleMessage: ${msg}  ${msg is Message.Event}" )
        return edgeEventHandler(msg)
    }
}

/*
================================================================
 ActorBasicFsm
================================================================
 */
class ActorBasicFsm(  qafsmname:  String,
                      scope: CoroutineScope = GlobalScope,
                      val initialStateName: String,
                      val buildbody: ActorBasicFsm.() -> Unit,
                      confined :    Boolean = false,
                      ioBound :     Boolean = false,
                      channelSize : Int = 50
                    ): ActorBasic(  qafsmname, scope, confined, ioBound, channelSize ) {

    val autoStartMsg = MsgUtil.buildDispatch(name, "autoStartSysMsg", "start", name)
    private var isStarted = false
    private lateinit var currentState: State
    var currentMsg = NoMsg

    private val stateList = mutableListOf<State>()
    private val msgQueueStore = mutableListOf<ApplMessage>()
    internal val msgQueue     = Channel<ApplMessage>(100) //LinkedListChannel

    //================================== STRUCTURAL =======================================
    fun state(stateName: String, build: State.() -> Unit) {
        val state = State(stateName)
        state.build()
        stateList.add(state)
    }

    private fun getStateByName(name: String): State {
        return stateList.firstOrNull { it.stateName == name }
            ?: throw NoSuchElementException(name)
    }
    //===========================================================================================

    init {
        buildbody()            //Build the structural part
        currentState = getStateByName(initialStateName)
        //println("ActorBasicFsm $name |  initialize currentState=${currentState.stateName}")
        scope.launch { autoMsg(autoStartMsg) }  //auto-start
    }

    override suspend fun actorBody(msg: ApplMessage) {
        println("ActorBasicFsm $name | msg=$msg")
        if( msg.msgId() == autoStartMsg.msgId() && ! isStarted ) {
            isStarted = true
            scope.launch{ fsmwork() }
        }else{
            msgQueue.send(msg)
        }
    }



    suspend fun fsmwork() {
        //println("ActorBasicFsm $name | work in STATE ${currentState.stateName}")
        //GlobalScope.launch {
        do {
            currentState.enterState()
            //println("ActorBasicFsm $name | TRANSITION in STATE ${currentState.stateName}")
            //EMPTY MOVE
            val nextState = checkTransition(NoMsg)
            if (nextState is State) currentState = nextState
            else currentState = transition()
        } while (true)
        //}
    }

    private suspend fun lookAtMsgQueue(): Pair<ApplMessage, State> {
        var nextState: State?
        do {
            val msg = msgQueue.receive()
            //println("ActorBasicFsm $name | lookAtMsgQueue received  $msg")
            nextState = checkTransition(msg)
            if (nextState is State) {
                return Pair(msg, nextState)
            } else { //EXCLUDE EVENTS FROM msgQueueStore
                if (!(msg.isEvent())) {
                    msgQueueStore.add(msg)
                    println("ActorBasicFsm $name | lookAtMsgQueue added $msg in msgQueueStore")
                } else {
                    println("ActorBasicFsm $name | DISCARDING THE EVENT: $msg")
                }
            }
        } while (true)
    }

    private fun checkTransition(msg: ApplMessage): State? {
        val edge = currentState.getEdgeForMessage(msg)
        //println("ActorBasicFsm $name | checkTransition ENTRY $msg , currentState=${currentState.name} edge=${edge is Edge}")
        return if (edge is Edge) {
            edge.enterEdge { getStateByName(it) }
        } else {
            //println("ActorBasicFsm $name | checkTransition NO next State for $msg !!!")
            null
        }
    }

    //Returns the next state by looking at th messages so far received or waits on infochannel
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    suspend fun transition(): State {
        //println("ActorBasicFsm $name | transition in:${currentState.stateName}")
        val state = lookAtMsgQueueStore()
        if (state is State) {
            //println("ActorBasicFsm $name | transition state from msgQueueStore:${state.stateName}")
            return state
        }
        println("ActorBasicFsm $name | WAITS in:${currentState.stateName}")
        val (msg, newstate) = lookAtMsgQueue() //infochannel.receive()
        println("ActorBasicFsm $name | RESUMES to:${newstate.stateName} from:${currentState.stateName} since:$msg")
        currentMsg = msg
        return newstate
    }

    private fun lookAtMsgQueueStore(): State? {
        //println("ActorBasicFsm $name | lookAtMsgQueueStore :${msgQueueStore.size}")
        msgQueueStore.forEach {
            val state = checkTransition(it)
            if (state is State) {
                msgQueueStore.remove(it)
                return state
            }
        }
        return null
    }

    /*
    fun isEvent( ev: Message, evName: String, v:String ) : Boolean{
        return  ev is Message.Event && ev.name == evName && ev.body==v
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
*/

    fun doswitch(): Edge.() -> Unit {
        return { edgeEventHandler = { true } }
    }
    fun fireIf(cond: (ApplMessage) -> Boolean): Edge.() -> Unit {
        return {
            edgeEventHandler = cond }
    }
    fun whenEvent(evName: String): Edge.() -> Unit {
        return {
            edgeEventHandler = { it.isEvent() && it.msgId() == evName } }
    }

    fun whenDispatch(msgName: String): Edge.() -> Unit {
        return {
            edgeEventHandler = { it.isDispatch() && it.msgId() == msgName }}
    }
}