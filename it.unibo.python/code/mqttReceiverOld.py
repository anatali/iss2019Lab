"""
mqttReceiver.py
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt

brokerAddr="192.168.1.6"
duration = 20
data     = []
counter  = 0
firstVal = 0
rot      = 0

def on_message(client, userdata, message) :   #define callback
    global counter, rot, data, firstVal   
    if rot > 90  : 
        print("done at counter=",counter)
        return
    value = str( message.payload.decode("utf-8")  )
    v    =  float( value )  
    if counter == 2 :
        firstVal  = v
    rot  = abs( v -  firstVal )
    print("rot:", rot )
    if rot < 90  :
        counter = counter + 1
        data.append( rot )  
    else :
        print("first",firstVal, "rot", rot, "count",counter)
        plt.plot(data)
        plt.show()   
"""       
    if counter < 30  :
        #print("value:", value  )
        counter = counter + 1
        rot = v-firstVal
        #print("rot:", rot )
        data.append( rot )    
        if rot >= 89.5  :                         #and rot <= 90.5
            print("rot at counter =", counter  )
    else :
        lastVal = v
        print("first",firstVal,"last",lastVal,"rot", rot, "count",counter)
        plt.plot(data)
        plt.show()
        counter  = 1
        data     = []
"""      
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/livestream")
client.subscribe("unibo/livestream")      #subscribe
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop