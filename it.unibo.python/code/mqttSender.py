"""
mqttSender.py
"""
import time
import paho.mqtt.client as paho
broker="192.168.1.6"
client= paho.Client("unibopythonclient") #create client object client1.on_publish = on_publish #assign function to callback client1.connect(broker,port) #establish connection client1.publish("house/bulb1","on")
print("connecting to broker ",broker)
client.connect(broker)                       #connect
time.sleep(1)
print("publishing ")
client.publish("unibo/livestream","2")         #publish
time.sleep(1)
print("publishing ")
client.publish("unibo/livestream","4")         #publish
time.sleep(1)
print("publishing ")
client.publish("unibo/livestream","9")         #publish
time.sleep(1)
client.disconnect()                        #disconnect
client.loop_stop()                           #stop loop
print("bye" )