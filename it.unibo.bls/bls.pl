%====================================================================================
% bls description   
%====================================================================================
context(ctxbls, "localhost",  "TCP", "8090" ).
context(ctxled, "localhost",  "TCP", "8095" ).
qactor( welcome, ctxbls, "it.unibo.welcome.Welcome").
qactor( led, ctxled, "it.unibo.bls19d.qak.LedActork").
qactor( control, ctxbls, "it.unibo.bls19d.qak.ControlActork").
qactor( button, ctxbls, "it.unibo.bls19d.qak.ButtonActork").
