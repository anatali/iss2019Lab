%====================================================================================
% roomboundaryplanned description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxboundaryplanned, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( onestepahead, ctxdummyformind, "external").
  qactor( roomboudaryexplorer, ctxboundaryplanned, "it.unibo.roomboudaryexplorer.Roomboudaryexplorer").
