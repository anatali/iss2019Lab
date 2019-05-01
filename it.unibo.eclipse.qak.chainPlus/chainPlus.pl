%====================================================================================
% chainPlus description   
%====================================================================================
context(ctxbutton, "192.168.43.229",  "TCP", "8010" ).
context(ctxledsplus, "192.168.43.229",  "TCP", "8050" ).
qactor( led1, ctxledsplus, "resources.LedActork").
qactor( led2, ctxledsplus, "resources.LedActork").
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
