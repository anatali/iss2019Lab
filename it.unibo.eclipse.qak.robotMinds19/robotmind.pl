%====================================================================================
% robotmind description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotmind, "localhost",  "MQTT", "0" ).
context(ctxbasicrobot, "localhost",  "TCP", "8005" ).
 qactor( resourcemodel, ctxrobotmind, "it.unibo.resourcemodel.Resourcemodel").
  qactor( robotmindapplication, ctxrobotmind, "it.unibo.robotmindapplication.Robotmindapplication").
  qactor( onecellforward, ctxrobotmind, "it.unibo.onecellforward.Onecellforward").
  qactor( robotmind, ctxrobotmind, "it.unibo.robotmind.Robotmind").
  qactor( sonarhandler, ctxrobotmind, "it.unibo.sonarhandler.Sonarhandler").
  qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
