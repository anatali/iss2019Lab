%====================================================================================
% blsBetter description   
%====================================================================================
mqttBroker("192.168.1.18", "1883").
context(ctxblsbetterconsole, "192.168.1.18",  "MQTT", "0" ).
context(ctxblsbetterled, "192.168.1.3",  "MQTT", "0" ).
qactor( button, ctxblsbetterconsole, "it.unibo.button.Button").
qactor( blscontrol, ctxblsbetterconsole, "it.unibo.blscontrol.Blscontrol").
qactor( led, ctxblsbetterled, "resources.LedActork").
