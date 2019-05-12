%====================================================================================
% robotWorker description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxworker, "localhost",  "MQTT", "0" ).
 qactor( robotplayer, ctxworker, "it.unibo.robotplayer.Robotplayer").
  qactor( mind, ctxworker, "it.unibo.mind.Mind").
  qactor( sonarhandler, ctxworker, "it.unibo.sonarhandler.Sonarhandler").
