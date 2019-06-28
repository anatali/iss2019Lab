%====================================================================================
% sonardatastream description   
%====================================================================================
mqttBroker("192.168.43.5", "1883").
context(ctxsonardata, "localhost",  "TCP", "8035" ).
 qactor( qakstream, ctxsonardata, "it.unibo.qakstream.Qakstream").
