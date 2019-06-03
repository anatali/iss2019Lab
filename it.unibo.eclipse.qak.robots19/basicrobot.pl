%====================================================================================
% basicrobot description   
%====================================================================================
mqttBroker("192.168.43.229", "1883").
context(ctxbasicrobot, "localhost",  "MQTT", "0" ).
 qactor( basicrobot, ctxbasicrobot, "it.unibo.basicrobot.Basicrobot").
  qactor( sonarhandler, ctxbasicrobot, "it.unibo.sonarhandler.Sonarhandler").
