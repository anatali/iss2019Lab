%====================================================================================
% chainPlus description   
%====================================================================================
context(ctxbutton, "localhost",  "TCP", "8010" ).
context(ctxledsplus, "localhost",  "TCP", "8050" ).
 qactor( led1, ctxledsplus, "resources.LedActork").
  qactor( led2, ctxledsplus, "resources.LedActork").
  qactor( button, ctxbutton, "it.unibo.button.Button").
  qactor( control, ctxbutton, "it.unibo.control.Control").
