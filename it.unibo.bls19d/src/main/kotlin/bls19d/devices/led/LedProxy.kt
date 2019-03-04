package it.unibo.bls19d.devices.led

import bls19d.messages.Protocol
import bls19d.messages.UtilsMsg
import java.util.Observable
import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.`is`.interfaces.protocols.IConnInteraction
import it.unibo.bls.interfaces.IObserver
import it.unibo.bls.utils.Utils
import it.unibo.bls19d.messages.ApplMessage
import it.unibo.blsFramework.concreteDevices.LedObserver
import it.unibo.blsFramework.models.LedModel
import it.unibo.supports.FactoryProtocol

class LedProxy(protected var protocol: Protocol,
               protected var hostName: String,
               protected var portNum: Int) : IObserver {

    protected var conn: IConnInteraction? = null

    init {
        configure()
    }

     fun configure() {
         when( protocol ){
             Protocol.SERIAL -> conn = UtilsMsg.getConnectionSerial("",9600 )
             Protocol.TCP , Protocol.UDP -> conn = UtilsMsg.getConnection( protocol, hostName , portNum, "ledProxy")
             else -> println("WARNING: protocol unknown")
         }
     }

    override fun update(source: Observable, value: Any) {
        when( "$value" ){
            "true"  ->  turnOn()
            "false" ->  turnOff()
        }
    }
    protected fun forward(msg : ApplMessage){
        conn?.sendALine( "$msg" )
    }

    protected fun turnOn() {
        forward( UtilsMsg.buildDispatch("true") )
    }

    protected fun turnOff() {
        forward( UtilsMsg.buildDispatch("false") )
    }


    companion object {
        //Factory method
        fun createLed(protocol: Protocol, hostName: String, portNum: Int): IObserver {
            return LedProxy(protocol, hostName, portNum)
        }

/*
 * Just for a rapid test
 */

        @JvmStatic
        fun main(args: Array<String>) {
            val protocol = Protocol.TCP
            val portNum = 8010
            //Create the led device
            val ledgui = LedAsGui.createLed(Utils.initFrame(200, 200))
            println("LED GUI CREATED")
            val ledFrontEnd = LedFrontEnd.createLed(protocol, portNum)
            val ledObs  = LedObserver()
            ledObs.setLed( ledgui )
            println("LED FRONT END CREATED")
            ledFrontEnd.addObserver(ledObs as IObserver)
            Utils.delay(1000)
            println("LED DEVICE CREATED")
            //Create the led client (proxy)
            val ledproxy = createLed(protocol, "localhost", portNum)
            println("LED PROXY CREATED")
            //Create the led model
            val ledmodel = LedModel.createLed(ledproxy)
            println("LED MODEL AND PROXY CREATED")
            //Do some interaction
            for (i in 1..3) {
                Utils.delay(250)
                ledmodel.turnOn()
                Utils.delay(250)
                ledmodel.turnOff()
            }
        }
    }


}