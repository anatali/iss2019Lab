context(ctxProducer,"localhost", "MQTT", 1883).
context(ctxConsumer,"localhost", "MQTT", 1883).

qactor( button,    ctxProducer, "it.unibo.qak.prodConsMqtt.ButtonGuiActork").
qactor( producer,  ctxProducer, "it.unibo.qak.prodConsMqtt.ProducerMqtt").
qactor( consumer1, ctxConsumer, "it.unibo.qak.prodConsMqtt.ConsumerMqtt").
%%qactor( consumer2, ctxConsumer, "it.unibo.qak.prodConsMqtt.ConsumerMqtt").

