%====================================================================================
% chain description   
%====================================================================================
context(ctxbutton, "localhost",  "TCP", "8010" ).
context(ctxleds1, "localhost",  "TCP", "8020" ).
context(ctxleds2, "localhost",  "TCP", "8020" ).
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
qactor( led1, ctxleds1, "it.unibo.led1.Led1").
qactor( led2, ctxleds2, "it.unibo.led2.Led2").
