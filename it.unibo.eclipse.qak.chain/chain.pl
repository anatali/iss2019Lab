%====================================================================================
% chain description   
%====================================================================================
context(ctxbutton, "localhost",  "TCP", "8010" ).
context(ctxleds1, "192.168.1.17",  "TCP", "8020" ).
context(ctxleds2, "192.168.1.18",  "TCP", "8030" ).
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
qactor( led1, ctxleds1, "it.unibo.led1.Led1").
qactor( led2, ctxleds1, "resources.LedActork").
qactor( led3, ctxleds2, "resources.LedActork").
