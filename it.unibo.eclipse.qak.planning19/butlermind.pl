%====================================================================================
% butlermind description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxbutlermind, "localhost",  "MQTT", "0" ).
context(ctxdummy, "otherhost",  "MQTT", "0" ).
 qactor( workerinroom, ctxdummy, "external").
  qactor( butlermind, ctxbutlermind, "it.unibo.butlermind.Butlermind").
