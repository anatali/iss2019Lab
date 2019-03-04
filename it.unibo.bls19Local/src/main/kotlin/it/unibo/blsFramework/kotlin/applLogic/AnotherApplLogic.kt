package it.unibo.blsFramework.kotlin.applLogic

class AnotherApplLogic : BlsApplicationLogic() {
    /*
       A simple led switching
    */
     override fun applLogic( ){
        numCalls++
        doBlink = numCalls % 2 != 0     //if false actorBlink ends its loop
        println("	AnotherApplLogic | execute numCalls=$numCalls doBlink=$doBlink")
        if ( led!!.getState() ) led!!.turnOff() else led!!.turnOn()
     }
}
