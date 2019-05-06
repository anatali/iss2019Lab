%%context(ctxProducer,"localhost", "TCP", 8010).
%%context(ctxConsumer,"localhost", "TCP", 8020).
mqttBroker( "localhost",  1883 ).

context(ctxProducer,"localhost", "TCP", "8010").
context(ctxConsumer,"localhost", "MQTT", "0"). %%The port is not considered here
qactor( button,    ctxProducer, "it.unibo.prodConsCustomMqtt.ButtonGuiActork").
qactor( producer,  ctxProducer, "it.unibo.prodConsCustomMqtt.ProducerMqtt").
qactor( consumer1, ctxConsumer, "it.unibo.prodConsCustomMqtt.ConsumerMqtt").
qactor( consumer2, ctxConsumer, "it.unibo.prodConsCustomMqtt.ConsumerMqtt").

