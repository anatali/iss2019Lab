%====================================================================================
% frontend description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxfrontend, "localhost",  "MQTT", "0" ).
context(ctxrobotmind, "otherhost",  "MQTT", "0" ).
 qactor( robotmind, ctxrobotmind, "external").
  qactor( frontend, ctxfrontend, "it.unibo.frontend.Frontend").
