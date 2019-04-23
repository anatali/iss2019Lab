system( sys0 ).

dispatch( m1 , m1(a) ).
dispatch( m2 , m2(X,Y) ).

context(ctxTest,"localhost",  "TCP", 8010).

%%qactor( control, ctxTest, "qastate.controlFsm").
qactor( led, ctxTest, "qastate.ledFsm").


