%====================================================================================
% robotBasic description   
%====================================================================================
context(ctxrobot, "localhost",  "TCP", "8010" ).
qactor( clienttcp, ctxrobot, "resources.clientWenvTcp").
qactor( button, ctxrobot, "it.unibo.button.Button").
qactor( robotcontrol, ctxrobot, "it.unibo.robotcontrol.Robotcontrol").
qactor( sonardetector, ctxrobot, "it.unibo.sonardetector.Sonardetector").
