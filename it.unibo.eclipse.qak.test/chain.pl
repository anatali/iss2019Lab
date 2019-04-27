%====================================================================================
% chain description   
%====================================================================================
context(ctxbutton, "localhost",  "TCP", "8010" ).
context(ctxleds1, "localhost",  "TCP", "8020" ).
qactor( button, ctxbutton, "it.unibo.button.Button").
qactor( control, ctxbutton, "it.unibo.control.Control").
qactor( clienttcp, ctxbutton, "resources.clientWenvTcp").
qactor( robotcontrol, ctxbutton, "it.unibo.robotControl.RobotControl").
qactor( led1, ctxleds1, "it.unibo.led1.Led1").
