%====================================================================================
% neuurons description   
%====================================================================================
context(ctxneurons, "localhost",  "TCP", "8022" ).
 qactor( a, ctxneurons, "it.unibo.a.A").
  qactor( b, ctxneurons, "it.unibo.b.B").
  qactor( c, ctxneurons, "it.unibo.c.C").
