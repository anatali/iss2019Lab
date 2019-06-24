%====================================================================================
% planex1 description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxplanex1, "localhost",  "MQTT", "0" ).
context(ctxdummyformind, "otherresourcelocalhost",  "MQTT", "0" ).
 qactor( resourcemodel, ctxdummyformind, "external").
  qactor( planex1, ctxplanex1, "it.unibo.planex1.Planex1").
  qactor( onecellforward, ctxplanex1, "it.unibo.onecellforward.Onecellforward").
