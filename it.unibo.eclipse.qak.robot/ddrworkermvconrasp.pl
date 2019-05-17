%====================================================================================
% ddrworkermvconrasp description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxsonaronrasp, "localhost",  "MQTT", "0" ).
 qactor( sonarhandleronrasp, ctxsonaronrasp, "it.unibo.sonarhandleronrasp.Sonarhandleronrasp").
