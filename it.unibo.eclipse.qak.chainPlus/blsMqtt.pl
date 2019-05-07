%====================================================================================
% blsMqtt description   
%====================================================================================
mqttBroker("192.168.1.18", "1883").
context(ctxmqttconsole, "localhost",  "TCP", "8035" ).
context(ctxmqttled, "localhost",  "TCP", "8045" ).
context(ctxmqttotherled, "localhost",  "TCP", "8065" ).
qactor( buttonmqtt, ctxmqttconsole, "it.unibo.buttonmqtt.Buttonmqtt").
qactor( blsmqttcontrol, ctxmqttconsole, "it.unibo.blsmqttcontrol.Blsmqttcontrol").
qactor( alarmmqtt, ctxmqttconsole, "it.unibo.alarmmqtt.Alarmmqtt").
qactor( ledmqtt, ctxmqttled, "it.unibo.ledmqtt.Ledmqtt").
qactor( otherled, ctxmqttotherled, "it.unibo.bls19d.qak.LedActork").
