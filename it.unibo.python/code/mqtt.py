import time
import paho.mqtt.client as paho
broker="broker.hivemq.com"
broker="192.168.1.6"
#define callback
def on_message(client, userdata, message):
    #time.sleep(1)
    print("received message =",str(message.payload.decode("utf-8")))

client= paho.Client("unibopythonclient")     #create client object client1.on_publish = on_publish #assign function to callback client1.connect(broker,port) #establish connection client1.publish("house/bulb1","on")
######Bind function to callback
client.on_message=on_message
#####
print("connecting to broker ",broker)
client.connect(broker)                       #connect
client.loop_start()                          #start loop to process received messages
print("subscribing ")
client.subscribe("sensornode/livestream")      #subscribe
"""
time.sleep(1)
print("publishing ")
client.publish("sensornode/livestream","d1")  #publish
time.sleep(1)
print("publishing ")
client.publish("sensornode/livestream","d2")  #publish
"""
time.sleep(60)
client.disconnect()                          #disconnect
print("bye")
client.loop_stop()                           #stop loop