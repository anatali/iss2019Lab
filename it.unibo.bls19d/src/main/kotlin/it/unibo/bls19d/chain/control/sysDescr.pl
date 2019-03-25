context(ctxControl,"localhost", "TCP",  8010).
context(ctxLeds,   "localhost", "TCP", 8020).

qactor( control, ctxControl, "it.unibo.bls19d.chain.control.ControlActor").
qactor( led1, ctxLeds, "it.unibo.bls19d.chain.led.LedActor" ).
qactor( led2, ctxLeds, "it.unibo.bls19d.chain.led.LedActor" ).
qactor( led3, ctxLeds, "it.unibo.bls19d.chain.led.LedActor" ).