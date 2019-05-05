%====================================================================================
% blsBetter description   
%====================================================================================
context(ctxblsbetterconsole, "192.168.1.18",  "TCP", "8075" ).
context(ctxblsbetterled, "192.168.1.3",  "TCP", "8065" ).
qactor( button, ctxblsbetterconsole, "it.unibo.button.Button").
qactor( blscontrol, ctxblsbetterconsole, "it.unibo.blscontrol.Blscontrol").
qactor( led, ctxblsbetterled, "resources.LedActork").
