package it.unibo.chain.model

import it.unibo.bls.interfaces.ILed
import it.unibo.blsFramework.concreteDevices.LedObserver
import it.unibo.blsFramework.interfaces.ILedModel
import it.unibo.blsFramework.interfaces.ILedObserver
import it.unibo.blsFramework.models.LedModel


class ChainModel( val ledObs : List<ILedObserver> ){  //each ledObs is connected to a real device
    private val ledModelList : MutableList<ILedModel> = mutableListOf<ILedModel>()
    init{
        createComponents()
    }
    fun createComponents(){
        println("ChainModel | creates ${ledObs.size} components")
        for( i in 0..ledObs.size-1 ){
            val ledm = LedModel.createLed()
            ledModelList.add( ledm )
            ledm.addObserver( ledObs.get(i) )
        }
    }

    fun turnOn( i : Int ){
        ledModelList.get(i).turnOn()
    }
    fun turnOff( i : Int ){
        ledModelList.get(i).turnOff()
    }
}