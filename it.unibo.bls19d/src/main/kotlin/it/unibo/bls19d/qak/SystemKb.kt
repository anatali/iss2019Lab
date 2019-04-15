package it.unibo.bls19d.qak

import it.unibo.kactor.ActorBasic

object SystemKb{
    val portNumber = 8012

    val blsActorMap : MutableMap<String, ActorBasic> =
        mutableMapOf<String, ActorBasic>()
}