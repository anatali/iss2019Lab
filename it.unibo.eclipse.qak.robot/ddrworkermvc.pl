%====================================================================================
% ddrworkermvc description   
%====================================================================================
mqttBroker("127.0.0.1", "1883").
context(ctxrobotmvc, "localhost",  "TCP", "8025" ).
context(ctxsonarhandler, "localhost",  "MQTT", "0" ).
 qactor( consolemvc, ctxrobotmvc, "it.unibo.consolemvc.Consolemvc").
  qactor( resourcemodel, ctxrobotmvc, "it.unibo.resourcemodel.Resourcemodel").
  qactor( robotmvc, ctxrobotmvc, "it.unibo.robotmvc.Robotmvc").
  qactor( sonarhandlermvc, ctxsonarhandler, "it.unibo.sonarhandlermvc.Sonarhandlermvc").
