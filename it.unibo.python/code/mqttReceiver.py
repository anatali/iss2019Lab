"""
mqttReceiver.py
---------------------------------------------------
C:\Didattica2018Work\iss2019Lab\it.unibo.python\code
python mqttReceiver.py
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt

#pip install playsound
from playsound import playsound  

brokerAddr="localhost"
duration = 20
data     = []
counter  = 0
firstVal = 0
rot      = 0

def on_message(client, userdata, message) :   #define callback
    global counter, rot, data, firstVal   
    if rot > 90  : 
        client.disconnect()             #disconnect
        return    
    msg      = str( message.payload.decode("utf-8")  )
    #msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
    msgitems = msg.split(",")
    value    = msgitems[5]
    v       =  float( msgitems[5] )  #5 => X (AZIMUTH for rotation)
    #print( v )
    if counter <= 2 :
        firstVal  = v
        rot       = 0
        print("first",firstVal, "rot", rot, "count",counter)
    rot  = abs( v -  firstVal )
    print("rot:", rot )
    if rot < 90  :
        counter = counter + 1
        data.append( rot )  
        if rot > 85  :
        	print("WARNING ... rot=", rot )
    else :
        print("first",firstVal, "rot", rot, "count",counter)
        playsound('../audio/tada2.wav')
        plt.plot(data)
        plt.show()   
    
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop        
