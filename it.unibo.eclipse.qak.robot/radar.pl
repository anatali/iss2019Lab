%====================================================================================
% radar description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxradar, "192.168.1.18",  "MQTT", "0" ).
 qactor( radar, ctxradar, "it.unibo.radar.Radar").
  qactor( radartester, ctxradar, "it.unibo.radartester.Radartester").
