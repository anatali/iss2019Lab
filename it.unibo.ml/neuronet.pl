%====================================================================================
% neuronet description   
%====================================================================================
context(ctxneuronet, "localhost",  "TCP", "8022" ).
 qactor( neuron, ctxneuronet, "it.unibo.neuron.Neuron").
  qactor( edge1neuron, ctxneuronet, "it.unibo.edge1neuron.Edge1neuron").
  qactor( edge2neuron, ctxneuronet, "it.unibo.edge2neuron.Edge2neuron").
  qactor( simul, ctxneuronet, "it.unibo.simul.Simul").
