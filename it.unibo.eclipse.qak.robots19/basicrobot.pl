%====================================================================================
% basicrobot description   
%====================================================================================
mqttBroker("192.168.1.4", "1883").
context(ctxbasicrobot, "localhost",  "MQTT", "0" ).
 qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxbasicrobot, "it.unibo.sonarhandler.Sonarhandler").
