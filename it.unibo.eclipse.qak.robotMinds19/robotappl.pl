%====================================================================================
% robotappl description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotappl, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( basicrobot, ctxdummyformind, "external").
  qactor( resourcemodel, ctxdummyformind, "external").
  qactor( onestepahead, ctxdummyformind, "external").
  qactor( robotmindapplication, ctxrobotappl, "it.unibo.robotmindapplication.Robotmindapplication").
