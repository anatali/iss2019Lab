%====================================================================================
% robotmind description   
%====================================================================================
mqttBroker("192.168.43.229", "1883").
context(ctxrobotmind, "localhost",  "MQTT", "0" ).
context(ctxdummyforappl, "otherapplhost",  "MQTT", "0" ).
 qactor( robotmindapplication, ctxdummyforappl, "external").
  qactor( resourcemodel, ctxrobotmind, "it.unibo.resourcemodel.Resourcemodel").
  qactor( robotmind, ctxrobotmind, "it.unibo.robotmind.Robotmind").
  qactor( sonarhandler, ctxrobotmind, "it.unibo.sonarhandler.Sonarhandler").
  qactor( basicrobot, ctxrobotmind, "it.unibo.basicrobot.Basicrobot").
