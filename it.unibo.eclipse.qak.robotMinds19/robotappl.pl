%====================================================================================
% robotappl description   
%====================================================================================
mqttBroker("192.168.43.229", "1883").
context(ctxrobotappl, "192.168.43.229",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( basicrobot, ctxdummyformind, "external").
  qactor( resourcemodel, ctxdummyformind, "external").
  qactor( robotmindapplication, ctxrobotappl, "it.unibo.robotmindapplication.Robotmindapplication").
  qactor( onecellforward, ctxrobotappl, "it.unibo.onecellforward.Onecellforward").
