package state

//Window -> Preferences -> General -> Editors -> Text Editors. TURN OFF Highlight current line .

/*
================================================================
 MESSAGES
================================================================
 */

public enum class MsgType{
	dispatch,
	request,
	event
}

sealed class Message (val mtype: MsgType, val name: String, val body: String,
					  val sender: String , val receiver: String, val num: String){
	 //val bodyT : Term = Term.createTerm(body)
 	 open class
	 Event(name: String, body: String="?", sender:String="any",receiver: String="none",num: String="0")
		 : Message(MsgType.event, name, body, sender, receiver, num)
 	 open class
	 Dispatch(name: String, body: String="?", sender: String="any",receiver: String="unknown",num: String="0") :
			 Message(MsgType.dispatch, name, body, sender, receiver,  num)
	 open class
	 Request(name: String, body: String="?",sender: String="any",receiver: String="unknown",num: String="0")
		 : Message(MsgType.request, name, body, sender, receiver, num)

	 override fun toString() : String { return "msg(${name},${mtype},${sender},${receiver},${body},${num})"}


 }
class SysMsg(): Message.Dispatch("sysMsg","sysMsg" )
class NoMsg(): Message.Dispatch("noMsg","noMsg" )

val dummyMsg  =  Message.Dispatch("dummy" )
val info      =  Message.Dispatch("info","info" )
val ev22      =  Message.Event("ev22","fire" )

fun dispatch(msgName:String, msgBogy:String) : Message.Dispatch{
	return Message.Dispatch(msgName,msgBogy)
}

fun event(msgName:String, msgBogy:String) : Message.Event{
	return Message.Event(msgName,msgBogy)
}
operator fun String.minus(other:String):Message.Dispatch{
 	return Message.Dispatch(this, other)
}
/*
SOFFRITTI msgs 
*/
val msgForward  = "{ 'type': 'moveForward', 'arg': 500 }";
val msgBackward = "{ 'type': 'moveBackward', 'arg': -1 }";
val msgStop     = "{ 'type': 'alarm', 'arg': 0 }";

fun msgStop( time: Int ) : String { return "{ 'type': 'alarm', 'arg': $time }" }
fun msgForward( time: Int ) : String { return "{ 'type': 'moveForward', 'arg': $time }" }
fun msgBackward( time: Int ) : String { return "{ 'type': 'moveBackward', 'arg': $time }" }

/*
fun Message.toString() : String{
	return "msg(${this.name}, ${this.mtype}, ${this.body},  ${this.num}"
}

fun Message.Event.toString() : String{
	return "msg(${this.name}, ${this.mtype}, ${this.body},  ${this.num}"
}
fun Message.Dispatch.toString() : String{
	return "msg(${this.name}, ${this.mtype}, ${this.body},  ${this.num}"
}
*/