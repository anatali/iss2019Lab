%====================================================================================
% mindexplore description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxappltest, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( appltest, ctxappltest, "it.unibo.appltest.Appltest").
