package state

/*
================================================================
 STATE
================================================================
 */
class State(val name: String) {
    private val edgeList = mutableListOf<Edge>()

    fun edge(name: String, targetState: String, cond: Edge.() -> Unit) {
        val edge = Edge(name, targetState)
		//println("                     edge $name $targetState")
         edge.cond() //set eventHandler (given by user) See fireIf
         edgeList.add(edge)
    }

    private val stateEnterAction  = mutableListOf<(State) -> Unit>()
 	
    //Add an action which will be called when the state is entered
    fun action( a: (State) -> Unit) {
     	println("State $name    | add action  $a")
        stateEnterAction.add(a)
    }

    operator fun plus (action: (State) -> Unit) {
        stateEnterAction.add(action)
    }
    fun addAction (action: (State) -> Unit) {
        stateEnterAction.add(action)
    }
	fun switch(name: String, targetState: String,
			   doaction: (State) -> Unit, cond: Edge.() -> Unit) {
		action( doaction )
		edge(name, targetState, cond)
	}	
 
    fun enterState() {
        println("State $name       | enterState ")
        stateEnterAction.forEach {  it(this)  }
    }

    //Get the appropriate Edge for the Message
    fun getEdgeForMessage(msg: Message): Edge? {
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
class Edge(val name: String, val targetState: String) {
    lateinit var eventHandler: (Message) -> Boolean


    private val actionList = mutableListOf<(Edge) -> Unit>()

    fun action(action: (Edge) -> Unit) {
    	//println("Edge         | add ACTION:  $action")
        actionList.add(action)
    }

    //Invoke when you go down the edge to another state
    fun enterEdge(retrieveState: (String) -> State): State {
    	//println("Edge         | enterEdge  retrieveState: ${retrieveState} actionList=$actionList")
        actionList.forEach { it(this) }         //MEALY?
        return retrieveState(targetState)
    }

    fun hasSwitch(): Boolean {
        //println("           Edge         | canHandleMessage: ${msg}  ${msg is Message.Event}" )
        return eventHandler( NoMsg() )
    }
    fun canHandleMessage(msg: Message): Boolean {
        //println("           Edge         | canHandleMessage: ${msg}  ${msg is Message.Event}" )
        return eventHandler(msg)
    }
}





