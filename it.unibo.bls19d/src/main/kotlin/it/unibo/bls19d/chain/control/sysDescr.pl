context(ctxControl,"locahost", "TCP",  8010).
context(ctxLeds,   "locahost", "TCP", 8020).

actor( led1, ctxLeds ).
actor( led2, ctxLeds ).
actor( led3, ctxLeds ).