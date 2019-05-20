%====================================================================================
% radar description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxradar, "localhost",  "MQTT", "0" ).
 qactor( radar, ctxradar, "it.unibo.radar.Radar").
