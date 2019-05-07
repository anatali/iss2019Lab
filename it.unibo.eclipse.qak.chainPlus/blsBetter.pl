%====================================================================================
% blsBetter description   
%====================================================================================
mqttBroker("192.168.1.18", "1883").
context(ctxblsbetter, "localhost",  "TCP", "8075" ).
qactor( button, ctxblsbetter, "it.unibo.button.Button").
qactor( blscontrol, ctxblsbetter, "it.unibo.blscontrol.Blscontrol").
qactor( led, ctxblsbetter, "it.unibo.bls19d.qak.LedActork").
