context(ctxControl,"localhost", "TCP", 8010).
context(ctxLeds,   "localhost", "TCP", 8020).
context(ctxOtherLeds,   "192.168.43.5", "TCP", 8030).

qactor( control, ctxControl, "it.unibo.bls19d.chain.ControlActor").
qactor( button,  ctxControl, "it.unibo.bls19d.chain.ButtonGuiActork").

qactor( led1, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).
qactor( led2, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).
qactor( led3, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).

qactor( led4, ctxOtherLeds, "it.unibo.bls19d.qak.LedActork" ).
qactor( led5, ctxOtherLeds, "it.unibo.bls19d.qak.LedActork" ).