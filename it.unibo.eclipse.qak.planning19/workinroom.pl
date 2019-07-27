%====================================================================================
% workinroom description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxworkinroom, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( onestepahead, ctxdummyformind, "external").
  qactor( butlermind, ctxworkinroom, "it.unibo.butlermind.Butlermind").
  qactor( butlertask, ctxworkinroom, "it.unibo.butlertask.Butlertask").
  qactor( workerinroom, ctxworkinroom, "it.unibo.workerinroom.Workerinroom").
