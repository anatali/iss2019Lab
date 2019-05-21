%====================================================================================
% robotmindnomvc description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotmindnomv, "localhost",  "MQTT", "0" ).
context(ctxdummyforbasicrobot, "otherhost",  "MQTT", "0" ).
 qactor( basicrobot, ctxdummyforbasicrobot, "external").
  qactor( robotmindnomvc, ctxrobotmindnomv, "it.unibo.robotmindnomvc.Robotmindnomvc").
