%====================================================================================
% neuurons description   
%====================================================================================
context(ctxneurons, "localhost",  "TCP", "8022" ).
 qactor( a, ctxneurons, "it.unibo.a.A").
  qactor( simul, ctxneurons, "it.unibo.simul.Simul").
