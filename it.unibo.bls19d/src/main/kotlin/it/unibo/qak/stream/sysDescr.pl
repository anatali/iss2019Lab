context(ctxProducer,"localhost",    "TCP", 8010).
context(ctxConsumer,"localhost", "TCP", 8020).

qactor( button,   ctxProducer, "it.unibo.qak.stream.ButtonGuiActork").
qactor( producer, ctxProducer, "it.unibo.qak.stream.ProducerStream").
qactor( consumer, ctxConsumer, "it.unibo.qak.stream.ConsumerSquare").

