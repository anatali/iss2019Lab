%====================================================================================
% chainPlus description   
%====================================================================================
context(ctxbutton, "192.168.1.18",  "TCP", "8010" ).
context(ctxledsplus, "192.168.1.18",  "TCP", "8050" ).
qactor( dynamo, ctxledsplus, "it.unibo.dynamo.Dynamo").
qactor( ledplus1, ctxbutton, "resources.LedActork").
