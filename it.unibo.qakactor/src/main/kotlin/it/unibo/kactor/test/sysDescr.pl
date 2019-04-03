context(ctxProdCons,"localhost", "TCP", 8010).

qactor( producer, ctxProdCons, "it.unibo.kactor.test.Producer").
qactor( consumer, ctxProdCons, "it.unibo.kactor.test.Consumer").

