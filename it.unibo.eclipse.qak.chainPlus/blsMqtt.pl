%====================================================================================
% blsMqtt description   
%====================================================================================
mqttBroker("192.168.1.18", "1883").
context(ctxmqttconsole, "localhost",  "MQTT", "0" ).
context(ctxmqttled, "localhost",  "MQTT", "0" ).
qactor( buttonmqtt, ctxmqttconsole, "it.unibo.buttonmqtt.Buttonmqtt").
qactor( blsmqttcontrol, ctxmqttconsole, "it.unibo.blsmqttcontrol.Blsmqttcontrol").
qactor( ledmqtt, ctxmqttled, "it.unibo.ledmqtt.Ledmqtt").
