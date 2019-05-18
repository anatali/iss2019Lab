%====================================================================================
% consolegui description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxconsolegui, "localhost",  "MQTT", "0" ).
 qactor( consolegui, ctxconsolegui, "it.unibo.consolegui.Consolegui").
