%====================================================================================
% chainPlus description   
%====================================================================================
context(ctxbutton, "192.168.43.229",  "TCP", "8010" ).
context(ctxledsplus, "192.168.43.229",  "TCP", "8050" ).
context(ctxledrasp, "192.168.43.18",  "TCP", "8040" ).
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
qactor( dynamo, ctxledsplus, "it.unibo.dynamo.Dynamo").
qactor( led1, ctxledsplus, "resources.LedActork").
qactor( led2, ctxledsplus, "resources.LedOnArduinoActork").
qactor( led3, ctxledrasp, "resources.LedOnRasp").
