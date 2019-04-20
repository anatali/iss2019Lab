context(ctxTest,"localhost",  "TCP", 8010).

%%qactor( control, ctxTest, "qastate.controlFsm").
qactor( led, ctxTest, "qastate.ledFsm").


