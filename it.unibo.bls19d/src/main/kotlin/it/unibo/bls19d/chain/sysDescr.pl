
%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%THREE NODES
%%%%%%%%%%%%%%%%%%%%%%%%%%%
context(ctxButton,"localhost", "TCP", 8010).
%%context(ctxLeds1, "localhost", "TCP", 8020).
%%context(ctxLeds2, "localhost", "TCP", 8030).

qactor( button,  ctxButton, "it.unibo.bls19d.chain.ButtonGuiActork").
qactor( control, ctxButton, "it.unibo.bls19d.chain.ControlActor").
qactor( led1, ctxButton, "it.unibo.bls19d.qak.LedActork" ).

%%qactor( led1, ctxLeds1, "it.unibo.bls19d.qak.LedActork" ).
%%qactor( led2, ctxLeds1, "it.unibo.bls19d.qak.LedActork" ).
%%qactor( led3, ctxLeds1, "it.unibo.bls19d.qak.LedActork" ).

%%qactor( led4, ctxLeds2, "it.unibo.bls19d.qak.LedActork" ).
%%qactor( led5, ctxLeds2, "it.unibo.bls19d.qak.LedActork" ).


/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%TWO NODES
%%%%%%%%%%%%%%%%%%%%%%%%%%%
context(ctxButton,  "localhost", "TCP", 8010).
context(ctxLeds,     "localhost", "TCP", 8020).

qactor( button,  ctxButton, "it.unibo.bls19d.chain.ButtonGuiActork").

qactor( control, ctxLeds, "it.unibo.bls19d.chain.ControlActor").
qactor( led1, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).
qactor( led2, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).
qactor( led3, ctxLeds, "it.unibo.bls19d.qak.LedActork" ).
*/