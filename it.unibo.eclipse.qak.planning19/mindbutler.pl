%====================================================================================
% mindbutler description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxmindbutler, "localhost",  "MQTT", "0" ).
context(ctxdummy, "otherhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummy, "external").
  qactor( onestepahead, ctxdummy, "external").
  qactor( butler, ctxmindbutler, "it.unibo.butler.Butler").
