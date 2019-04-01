/*
%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%THREE NODES
%%%%%%%%%%%%%%%%%%%%%%%%%%%
context(ctxButton,"localhost", "TCP", 8010).
context(ctxLeds1, "localhost", "TCP", 8020).
context(ctxLeds2, "localhost", "TCP", 8030).

qactor( button,  ctxButton, "it.unibo.kactor.test.chain.ButtonGuiActork").
qactor( control, ctxButton, "it.unibo.kactor.test.chain.ControlActor").

qactor( led1, ctxLeds1, "it.unibo.kactor.test.chain.LedActork" ).
qactor( led2, ctxLeds1, "it.unibo.kactor.test.chain.LedActork" ).
qactor( led3, ctxLeds1, "it.unibo.kactor.test.chain.LedActork" ).

qactor( led4, ctxLeds2, "it.unibo.kactor.test.chain.LedActork" ).
qactor( led5, ctxLeds2, "it.unibo.kactor.test.chain.LedActork" ).
*/


%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%TWO NODES
%%%%%%%%%%%%%%%%%%%%%%%%%%%
context(ctxButton,  "localhost", "TCP", 8010).
context(ctxLeds,     "localhost", "TCP", 8020).

qactor( button,  ctxButton, "it.unibo.kactor.test.chain.ButtonGuiActork").

qactor( control, ctxLeds, "it.unibo.kactor.test.chain.ControlActor").
qactor( led1, ctxLeds, "it.unibo.kactor.test.chain.LedActork" ).
qactor( led2, ctxLeds, "it.unibo.kactor.test.chain.LedActork" ).
qactor( led3, ctxLeds, "it.unibo.kactor.test.chain.LedActork" ).
