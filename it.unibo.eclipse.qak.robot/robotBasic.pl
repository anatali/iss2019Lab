%====================================================================================
% robotBasic description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxrobot, "localhost",  "TCP", "8010" ).
context(ctxradar, "localhost",  "TCP", "8015" ).
 qactor( clienttcp, ctxrobot, "resources.clientWenvTcp").
  qactor( button, ctxrobot, "it.unibo.button.Button").
  qactor( robotcontrol, ctxrobot, "it.unibo.robotcontrol.Robotcontrol").
  qactor( sonardetector, ctxrobot, "it.unibo.sonardetector.Sonardetector").
  qactor( radar, ctxradar, "it.unibo.radar.Radar").
