%====================================================================================
% neuronsimul description   
%====================================================================================
context(ctxneuronsimul, "localhost",  "TCP", "8042" ).
 qactor( aneuron, ctxneuronsimul, "it.unibo.aneuron.Aneuron").
  qactor( stimulator, ctxneuronsimul, "it.unibo.stimulator.Stimulator").
