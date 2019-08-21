"""
python plotGY521.py
"""
import time
import paho.mqtt.client as paho
 
brokerAddr="localhost"
duration = 15
msgnum   = 0

def on_message(client, userdata, message) :   #define callback
 	#msg(g521,event, gyroSender, none, g521(TYPE,X,Y,Z),N)
	evMsg = str( message.payload.decode("utf-8")  )
	print( evMsg  )

client=paho.Client("plotGY521")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)

print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe

client.loop_start()             #start loop to process received messages
time.sleep(duration)
#client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    