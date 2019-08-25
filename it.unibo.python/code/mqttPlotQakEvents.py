"""
mqttPlotQakEvents.py
---------------------------------------------------
C:\Didattica2018Work\iss2019Lab\it.unibo.python\code
python mqttPlotQakEvents.py
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt

brokerAddr="localhost"
duration = 20
x        = []
y        = []
z        = []
goon     = True
msgnum   = 0 
def on_message(client, userdata, message) :   #define callback
	global msgnum,  x,y,z, goon
	if not goon :
		return
	#msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
    #msg(g521,event, gyroSender, none, g521(TYPE,X,Y,Z),MSGNUM)
	evMsg = str( message.payload.decode("utf-8")  )
	msgitems = evMsg.split(",")
	if msgnum < 19  :
		print("evMsg=", evMsg, "msgnum=", msgnum )
		msgnum = msgnum + 1
		x.append( float( msgitems[5] ) )
		y.append( float( msgitems[6] ) )
		vz = float( msgitems[7].split(')')[0] )
		z.append( vz )
	else  :
		print("evMsg=", evMsg, "msgnum=", msgnum )
		plt.plot(list(x), color='red')
		plt.plot(list(y), color='green')
		plt.plot(list(z), color='blue')
		plt.show()
		goon = False
		client.disconnect()
    
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback

client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe

print("collecting values; please wait ..." )
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    
