package it.unibo.kactor

import kotlinx.coroutines.*
import java.util.NoSuchElementException

/*
================================================================
 STATE
================================================================
 */
class State(val stateName : String ) {
    private val edgeList          = mutableListOf<Transition>()
    private val stateEnterAction  = mutableListOf< (State) -> Unit>()
    private val myself : State    = this

    fun transition(edgeName: String, targetState: String, cond: Transition.() -> Unit) {
        val trans = Transition(edgeName, targetState)
        //println("      trans $name $targetState")
        trans.cond() //set eventHandler (given by user) See fireIf
        edgeList.add(trans)
    }
    //Add an action which will be called when the state is entered
    fun action(  a:  (State) -> Unit) {
        //println("State $stateName    | add action  $a")
        stateEnterAction.add( a )
    }
    fun addAction (action:  (State) -> Unit) {
        stateEnterAction.add(action)
    }
    fun enterState() {
        stateEnterAction.forEach {  it(this) }
     }

    //Get the appropriate Edge for the Message
    fun getTransitionForMessage(msg: ApplMessage): Transition? {
        //println("State $name       | getTransitionForMessage  $msg  list=${edgeList.size} ")
        val first = edgeList.firstOrNull { it.canHandleMessage(msg) }
        return first
    }
}

/*
================================================================
 EDGE
================================================================
 */
class Transition(val edgeName: String, val targetState: String) {

    lateinit var edgeEventHandler: (ApplMessage) -> Boolean
    private val actionList = mutableListOf<(Transition) -> Unit>()

    fun action(action: (Transition) -> Unit) { //MEALY?
        //println("Transition  | add ACTION:  $action")
        actionList.add(action)
    }

    //Invoke when you go down the transition to another state
    fun enterTransition(retrieveState: (String) -> State): State {
        //println("Transition  | enterEdge  retrieveState: ${retrieveState} actionList=$actionList")
        actionList.forEach { it(this) }         //MEALY?
        return retrieveState(targetState)
    }

    fun canHandleMessage(msg: ApplMessage): Boolean {
        //println("Transition  | canHandleMessage: ${msg}  ${msg is Message.Event}" )
        return edgeEventHandler(msg)
    }
}

/*
================================================================
 ActorBasicFsm
================================================================
 */
abstract class ActorBasicFsm(  qafsmname:  String,
                      fsmscope: CoroutineScope = GlobalScope,
                      //val initialStateName: String,
                      //val buildbody: ActorBasicFsm.() -> Unit,
                      confined :    Boolean = false,
                      ioBound :     Boolean = false,
                      channelSize : Int = 50
                    ): ActorBasic(  qafsmname, fsmscope, confined, ioBound, channelSize ) {

    val autoStartMsg = MsgUtil.buildDispatch(name, "autoStartSysMsg", "start", name)
    private var isStarted = false
    private lateinit var currentState: State
    var currentMsg = NoMsg
    lateinit var mybody : ActorBasicFsm.() -> Unit


    private val stateList = mutableListOf<State>()
    private val msgQueueStore = mutableListOf<ApplMessage>()
    //internal val msgQueue     = Channel<ApplMessage>(100) //LinkedListChannel

    //================================== STRUCTURAL =======================================
    fun state(stateName: String, build: State.() -> Unit) {
        val state = State(stateName )
        state.build()
        stateList.add(state)
    }

    private fun getStateByName(name: String): State {
        return stateList.firstOrNull { it.stateName == name }
            ?: throw NoSuchElementException(name)
    }
    //===========================================================================================

    init {
        //println("ActorBasicFsm INIT")
        setBody( getBody(), getInitialState() )
        /*
        buildbody()            //Build the structural part
        currentState = getStateByName(initialStateName)
        //println("ActorBasicFsm $name |  initialize currentState=${currentState.stateName}")
        fsmscope.launch { autoMsg(autoStartMsg) }  //auto-start
        */
    }

    abstract fun getBody() : (ActorBasicFsm.() -> Unit)
    abstract fun getInitialState() : String

    fun setBody( buildbody: ActorBasicFsm.() -> Unit,
                         initialStateName: String ){
        buildbody()            //Build the structural part
        currentState = getStateByName( initialStateName )
        //println("ActorBasicFsm $name |  initialize currentState=${currentState.stateName}")
        scope.launch { autoMsg(autoStartMsg) }  //auto-start
    }

    override suspend fun actorBody(msg: ApplMessage) {
        //println("ActorBasicFsm $name | msg=$msg")
        if( msg.msgId() == autoStartMsg.msgId() && ! isStarted ) {
            //scope.launch{ fsmwork() } //The actot must continue to receive msgs
            fsmStartWork()
            //println("ActorBasicFsm $name | BACK TO MAIN ACTOR AFTER INIT")
        }else{
            fsmwork(msg)
            //println("ActorBasicFsm $name | BACK TO MAIN ACTOR")
        }
    }

    fun hanldeCurrentMessage(msg : ApplMessage, nextState : State?, memo : Boolean = true) : Boolean{
        //println("ActorBasicFsm $name | hanldeCurrentMessage in ${currentState.stateName} msg=${msg.msgId()}")
        if (nextState is State) {
            currentMsg   = msg
            currentState = nextState
            return true
        } else { //EXCLUDE EVENTS FROM msgQueueStore
            if( ! memo ) return false
            if (!(msg.isEvent()) ) {
                msgQueueStore.add(msg)
                println("ActorBasicFsm $name |  added $msg in msgQueueStore")
             } else  println("ActorBasicFsm $name | DISCARDING THE EVENT: $msg")
            return false
        }
    }

    fun checkDoEmptyMove(){
        var nextState = checkTransition(NoMsg) //EMPTY MOVE
        while (nextState is State) {
            currentMsg   = NoMsg
            currentState = nextState
            currentState.enterState()
            nextState = checkTransition(NoMsg) //EMPTY MOVE
        }
    }

    fun fsmStartWork() {
        isStarted = true
        //println("ActorBasicFsm $name | fsmStartWork in STATE ${currentState.stateName}")
        currentState.enterState()
        checkDoEmptyMove()
    }

    fun fsmwork(msg : ApplMessage) {
        //println("ActorBasicFsm $name | fsmwork in ${currentState.stateName} ")
        var nextState = checkTransition(msg)
        var b = hanldeCurrentMessage( msg, nextState )
        while(  b  ){ //handle previous messages
            currentState.enterState()
            checkDoEmptyMove()
            val nextState = lookAtMsgQueueStore()
            b = hanldeCurrentMessage( msg, nextState, memo=false )
         }
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

    private fun checkTransition(msg: ApplMessage): State? {
        val trans = currentState.getTransitionForMessage(msg)
        //println("ActorBasicFsm $name | checkTransition ENTRY $msg , currentState=${currentState.name} edge=${edge is Edge}")
        return if (trans is Transition) {
            trans.enterTransition { getStateByName(it) }
        } else {
            //println("ActorBasicFsm $name | checkTransition NO next State for $msg !!!")
            null
        }
    }


    /*
    //Returns the next state by looking at th messages so far received or waits on infochannel
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    suspend fun transition(): State {
        //println("ActorBasicFsm $name | transition in:${currentState.stateName}")
        val state = lookAtMsgQueueStore()
        if (state is State) {
            //println("ActorBasicFsm $name | transition state from msgQueueStore:${state.stateName}")
            return state
        }else return State("nostate",scope)
        //lookAtMsgQueue => terminate

        val (msg, newstate) = lookAtMsgQueue()
        currentMsg = msg
        return newstate

    }

    private suspend fun lookAtMsgQueue(): Pair<ApplMessage, State> {
        var nextState: State?
        do {
            println("ActorBasicFsm $name | lookAtMsgQueue in:${currentState.stateName}")
            val msg = msgQueue.receive()
            println("ActorBasicFsm $name | lookAtMsgQueue received  $msg")
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

*/


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

    fun doswitch(): Transition.() -> Unit {
        return { edgeEventHandler = { true } }
    }
    fun fireIf(cond: (ApplMessage) -> Boolean): Transition.() -> Unit {
        return {
            edgeEventHandler = cond }
    }
    fun whenEvent(evName: String): Transition.() -> Unit {
        return {
            edgeEventHandler = { it.isEvent() && it.msgId() == evName } }
    }

    fun whenDispatch(msgName: String): Transition.() -> Unit {
        return {
            edgeEventHandler = { it.isDispatch() && it.msgId() == msgName }}
    }
}