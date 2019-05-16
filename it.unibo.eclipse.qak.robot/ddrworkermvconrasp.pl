%====================================================================================
% ddrworkermvconrasp description   
%====================================================================================
mqttBroker("192.168.43.229", "1883").
context(ctxsonaronrasp, "localhost",  "MQTT", "0" ).
 qactor( sonarhandleronrasp, ctxsonaronrasp, "it.unibo.sonarhandleronrasp.Sonarhandleronrasp").
