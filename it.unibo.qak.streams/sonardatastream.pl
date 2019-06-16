%====================================================================================
% sonardatastream description   
%====================================================================================
mqttBroker("192.168.43.5", "1883").
context(ctxsonardata, "localhost",  "MQTT", "0" ).
 qactor( qakstream, ctxsonardata, "it.unibo.qakstream.Qakstream").
