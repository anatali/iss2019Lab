%====================================================================================
% ddrWorker description   
%====================================================================================
context(ctxrobotreq, "localhost",  "TCP", "8025" ).
 qactor( consolereq, ctxrobotreq, "it.unibo.consolereq.Consolereq").
  qactor( robotreq, ctxrobotreq, "it.unibo.robotreq.Robotreq").
  qactor( sonarhandler, ctxrobotreq, "it.unibo.sonarhandler.Sonarhandler").
  qactor( radarreq, ctxrobotreq, "it.unibo.radarreq.Radarreq").
