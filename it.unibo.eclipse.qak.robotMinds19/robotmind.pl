%====================================================================================
% robotmind description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotmind, "localhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxrobotmind, "it.unibo.resourcemodel.Resourcemodel").
  qactor( robotmind, ctxrobotmind, "it.unibo.robotmind.Robotmind").
  qactor( sonarhandler, ctxrobotmind, "it.unibo.sonarhandler.Sonarhandler").
  qactor( basicrobot, ctxrobotmind, "it.unibo.basicrobot.Basicrobot").
