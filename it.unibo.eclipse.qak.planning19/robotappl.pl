%====================================================================================
% robotappl description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxrobotappl, "localhost",  "MQTT", "0" ).
 qactor( robotmindapplication, ctxrobotappl, "it.unibo.robotmindapplication.Robotmindapplication").
