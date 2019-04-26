%====================================================================================
% chain description   
%====================================================================================
context(ctxbutton, "localhost",  "TCP", "8010" ).
context(ctxleds1, "localhost",  "TCP", "8020" ).
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
qactor( led, ctxleds1, "it.unibo.led.Led").
qactor( led1, ctxleds1, "it.unibo.led1.Led1").
