%====================================================================================
% consolegui description   
%====================================================================================
mqttBroker("192.168.1.4", "1883").
context(ctxconsolegui, "localhost",  "MQTT", "0" ).
 qactor( consolegui, ctxconsolegui, "it.unibo.consolegui.Consolegui").
