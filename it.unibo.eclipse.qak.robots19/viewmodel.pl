%====================================================================================
% viewmodel description   
%====================================================================================
mqttBroker("localhost", "1883").
context(ctxviewmodel, "localhost",  "MQTT", "0" ).
 qactor( viewmodel, ctxviewmodel, "it.unibo.viewmodel.Viewmodel").
