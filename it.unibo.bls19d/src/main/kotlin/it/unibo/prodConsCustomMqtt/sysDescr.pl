context(ctxProducer,"localhost", "TCP", 8010).
context(ctxConsumer,"localhost", "TCP", 8020).

qactor( button,    ctxProducer, "it.unibo.prodConsCustomMqtt.ButtonGuiActork").
qactor( producer,  ctxProducer, "it.unibo.prodConsCustomMqtt.ProducerMqtt").
qactor( consumer1, ctxConsumer, "it.unibo.prodConsCustomMqtt.ConsumerMqtt").
qactor( consumer2, ctxConsumer, "it.unibo.prodConsCustomMqtt.ConsumerMqtt").

