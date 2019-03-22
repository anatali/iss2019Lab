package it.unibo.bls19d.chain.control

import alice.tuprolog.Struct
import alice.tuprolog.Term
import it.unibo.bls19d.chain.ChainMsg
import it.unibo.bls19d.chain.ChainRegister
import it.unibo.bls19d.chain.led.LedProxy
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.Protocol
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import it.unibo.bls19d.chain.LedMsg.*
import it.unibo.bls19d.chain.elements.ChainElements

class ChainCentralControl( name: String ) : ActorBasic(name) {
protected val ledList : ArrayList<LedProxy>  = ArrayList<LedProxy>()
protected val TIMEON : Long = 1000

    override suspend fun actorBody( msg : ApplMessage ){
        println("ChainCentralControl | RECEIVED: $msg")
        when( msg.msgId() ){
           "chainRegister" -> {
               println("${msg.msgContent()}")
               val t = Term.createTerm( msg.msgContent() )
               val ts = t as Struct
               val host=ts.getArg(0).toString()
               val port=ts.getArg(1).toString()
               val protocol = ts.getArg(2).toString()
               val portNum = Integer.parseInt(port)

               ledList.add( LedProxy("led", Protocol.TCP, host, portNum) )
               println("ChainCentralControl | chainRegister ${host} , $portNum")
           }
            ChainMsg.startChainBlink.name -> {
                println("ChainCentralControl | blink listSize=${ledList.size}")
                ledList.forEach{
                    it.forward( startBlink.name, startBlink.cmd,it)
                    delay( TIMEON )
                }
            }
            ChainMsg.stopChainBlink.name -> {
                println("ChainCentralControl | no blink")
                ledList.forEach{
                    it.forward( stopBlink.name, stopBlink.cmd,it)
                }
            }
            else -> println("Unknown")
        }
    }
}


//registerLed( hostName, port )

fun main() : Unit = runBlocking{

    val hostName  = "localhost"
    val protocol  = Protocol.TCP
    val portNum   = 8000


    /*
    1) ACTIVATE ChainElements activated


    if( ! ChainElements.activated ){
        ChainElements()
        delay( 2000 )
    }
     */
    /*
    Each element of the chain must be registered to the controller
     */
    println(" -------------- REGISTER ---------------------- ")
    val ccc    = ChainCentralControl("ccc")
    val ledNum = ChainElements.numOfElements
    for( i in 1.. ledNum ){
        val ledPort   = portNum+i*10
        val msg       = ChainRegister("localhost", ledPort.toString())
        ccc.forward(msg.msgId, msg.toString(), ccc)
    }
    delay(1000)
    println(" -------------- BLINK  ---------------------- ")
    ccc.forward(
        ChainMsg.startChainBlink.name,
        ChainMsg.startChainBlink.name, ccc)
    delay(7000)
    ccc.forward(
        ChainMsg.stopChainBlink.name,
        ChainMsg.stopChainBlink.name, ccc)

    delay(3000)
}