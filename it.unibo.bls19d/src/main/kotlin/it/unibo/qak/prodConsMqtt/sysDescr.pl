mqttBroker( "localhost",  1883 ).

context(ctxProducer,"localhost", "TCP", 8010).
context(ctxConsumer,"localhost", "MQTT", 0). %%The port is not considered here

qactor( button,    ctxProducer, "it.unibo.qak.prodConsMqtt.ButtonGuiActork").
qactor( producer,  ctxProducer, "it.unibo.qak.prodConsMqtt.ProducerMqtt").
qactor( consumer1, ctxConsumer, "it.unibo.qak.prodConsMqtt.ConsumerMqtt").
qactor( consumer2, ctxConsumer, "it.unibo.qak.prodConsMqtt.ConsumerMqtt").

