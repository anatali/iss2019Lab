context(ctxProducer,"localhost",    "TCP", 8010).
context(ctxConsumer,"localhost", "TCP", 8020).

%%qactor( button,   ctxProducer, "it.unibo.qak.producer.ButtonGuiActork").
qactor( producer, ctxProducer, "it.unibo.qak.producer.Producer").
qactor( consumer, ctxConsumer, "it.unibo.qak.consumer.Consumer").

