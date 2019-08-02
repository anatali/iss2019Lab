%====================================================================================
% firndthetable description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxroomexplore, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( onestepahead, ctxdummyformind, "external").
  qactor( roomexplorer, ctxroomexplore, "it.unibo.roomexplorer.Roomexplorer").
