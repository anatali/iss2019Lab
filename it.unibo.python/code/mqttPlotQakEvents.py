"""
mqttPlotQakEvents.py
---------------------------------------------------
C:\Didattica2018Work\iss2019Lab\it.unibo.python\code
python mqttReceiverQakEvent.py
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt

brokerAddr="192.168.1.6"
duration = 20
data     = []
counter  = 0 
def on_message(client, userdata, message) :   #define callback
	global counter,  data
	#msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
	evMsg = str( message.payload.decode("utf-8")  )
	#print("evMsg=", evMsg )
	msgitems = evMsg.split(",")
	v        =  float( msgitems[5] ) 
	print("v=", v )
	if counter	< 20  :
		counter = counter + 1
		data.append( v )
	else  :
		plt.plot(data)
		plt.show()
		data = []
		counter = 0       
 	
    
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
