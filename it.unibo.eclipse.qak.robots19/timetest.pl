%====================================================================================
% timetest description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxtimetest, "localhost",  "MQTT", "0" ).
 qactor( timetester, ctxtimetest, "it.unibo.timetester.Timetester").
