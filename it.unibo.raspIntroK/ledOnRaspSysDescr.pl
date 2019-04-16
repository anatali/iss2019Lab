context(ctxLed,    "192.168.1.8",  "TCP", 8020).
context(ctxButton, "192.168.1.5",  "TCP", 8010).

%%context(ctxLed,    "192.168.1.8",   "TCP", 8020).
%%context(ctxButton, "192.168.1.5",  "MQTT", 1883).

qactor( button,   ctxButton, "it.unibo.qak.led.ButtonGuiActork").
qactor( led,      ctxLed,    "it.unibo.qak.led.LedOnRasp").
qactor( control,  ctxLed,    "it.unibo.qak.led.ControlActork").


