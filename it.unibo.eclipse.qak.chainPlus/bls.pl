%====================================================================================
% bls description   
%====================================================================================
context(ctxbls, "localhost",  "TCP", "8090" ).
qactor( button, ctxbls, "resources.bls.ButtonActork").
qactor( control, ctxbls, "resources.bls.ControlActork").
qactor( led, ctxbls, "resources.LedActork").
