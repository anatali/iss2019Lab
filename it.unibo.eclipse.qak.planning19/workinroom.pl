%====================================================================================
% workinroom description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxworkinroom, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( workerinroom, ctxworkinroom, "it.unibo.workerinroom.Workerinroom").
  qactor( onecellforward, ctxworkinroom, "it.unibo.onecellforward.Onecellforward").
