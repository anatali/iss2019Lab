%====================================================================================
% robotBasic description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxrobot, "localhost",  "TCP", "8010" ).
context(ctxradar, "192.168.1.18",  "MQTT", "0" ).
 qactor( clienttcp, ctxrobot, "resources.clientWenvTcp").
  qactor( button, ctxrobot, "it.unibo.button.Button").
  qactor( robotcontrol, ctxrobot, "it.unibo.robotcontrol.Robotcontrol").
  qactor( sonardetector, ctxrobot, "it.unibo.sonardetector.Sonardetector").
  qactor( radar, ctxradar, "external").
