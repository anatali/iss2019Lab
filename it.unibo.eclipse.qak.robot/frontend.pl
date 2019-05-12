%====================================================================================
% frontend description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxfrontend, "localhost",  "MQTT", "0" ).
 qactor( frontend, ctxfrontend, "it.unibo.frontend.Frontend").
