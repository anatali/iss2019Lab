package it.unibo.bls19d.devices.led

import bls19d.messages.Protocol
import bls19d.messages.UtilsMsg
import it.unibo.supports.FactoryProtocol
import it.unibo.bls.interfaces.IObservable
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls19d.messages.ApplMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LedFrontEnd(  val protocol: Protocol, val portNum: Int) : Observable(), IObservable {
    protected var hostName: String? = null
    protected var factoryProtocol: FactoryProtocol? = null

    init {
        factoryProtocol = UtilsMsg.getFactoryProtocol(protocol)
        doJob()
    }

    //from IObservable
    override fun addObserver(obs: IObserver) {
        super.addObserver(obs)
    }


    protected fun doJob() {
        GlobalScope.launch {
            try {
                println("   LedFrontEnd | STARTED")
                val conn = factoryProtocol!!.createServerProtocolSupport(portNum) //BLOCKS
                while (true) {
                    val msg = conn.receiveALine()       //BLOCKING
                    println("   LedFrontEnd | receives:$msg")
                    val inputmsg = ApplMessage(msg)
                    setChanged()
                    notifyObservers( inputmsg.msgContent() )
                }
            } catch (e: Exception) {
                    e.printStackTrace() }
        }
    }

    companion object {
        //Factory method
        fun createLed(protocol: Protocol, portNum: Int): IObservable {
            return LedFrontEnd(protocol, portNum)
        }
    }
 }
//Just for a rapid test: see LedProxy