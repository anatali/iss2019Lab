%====================================================================================
% frontend description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxfrontend, "localhost",  "MQTT", "0" ).
context(ctxrobotmindnomv, "otherhost",  "MQTT", "0" ).
 qactor( robotmind, ctxrobotmindnomv, "external").
  qactor( frontend, ctxfrontend, "it.unibo.frontend.Frontend").
