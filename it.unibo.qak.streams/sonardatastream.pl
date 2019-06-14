%====================================================================================
% sonardatastream description   
%====================================================================================
context(ctxsonardata, "localhost",  "TCP", "8035" ).
 qactor( qakstream, ctxsonardata, "it.unibo.qakstream.Qakstream").
  qactor( qaksonardatahandler, ctxsonardata, "it.unibo.qaksonardatahandler.Qaksonardatahandler").
