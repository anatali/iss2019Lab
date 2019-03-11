package it.unibo.blsFramework.kotlin.fsm

import it.unibo.bls.devices.gui.ButtonAsGui
import it.unibo.bls.devices.gui.LedAsGui
import it.unibo.bls.utils.Utils
import kotlinx.coroutines.runBlocking

fun main( ) = runBlocking {
val blSystem =
    BlsActorFramework.createTheSystem( "sys0",
        ButtonAsGui.createButton("click"),
        LedAsGui.createLed()
    )
    //Add a second Led
    blSystem.addConcreteLed( LedAsGui.createLed() );

    Utils.delay(15000);
    println(" ================== CHANGE CONTROL ================= ");
    blSystem.changeApplLogic( AnotherBlsApplLogic() ) ;

//blSystem.addConcreteLed(LedProxyArduino.create(DeviceConfig.serialPortNum, DeviceConfig.serialBaudrate));
//blSystem.addConcreteButton(ButtonAsGui.createButton("LedControl"))

}



