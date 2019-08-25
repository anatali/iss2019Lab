"""
gyro90.py
"""
import time
import paho.mqtt.client as paho

brokerAddr="localhost"
goon      = True
startTime = 0
dt        = 0.2
angle     = 0.0

def sendDoneMsg( client  ) :
	msgout = "msg( gyro90done,event,gyro90,none,gyro90done,1 )"
	client.publish("unibo/qak/events", msgout, 0, retain=False);		


def on_message(client, userdata, message) :   #define callback
	global goon,  startTime, angle
	now     = time.time() 
	elapsed = now-startTime
	#print( "now=" , now, "elapsed=",  elapsed)
	startTime = now
 	#msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
	#msg(g521,event, gyroSender, none, g521(TYPE,X,Y,Z),MSGNUM)
	evMsg = str( message.payload.decode("utf-8")  )
	#print("evMsg=", evMsg )
	msgitems = evMsg.split(",")
 	vz = float( msgitems[7].split(')')[0] )
 	da    = abs(vz) * elapsed
	angle = angle + da
 	print("vz=", ("%.3f" % vz), "angle=", ("%.3f" % angle)  )
	#print("elapsed=", ("%.2f" % elapsed), "da=", ("%.2f" % da) )
	startTime = time.time() 
	if angle > 90.0 and goon :
 		goon = False
 		sendDoneMsg( client )
     
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe
startTime = time.time() 

client.loop_start()             #start loop to process received messages
while goon :
	time.sleep(dt)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop      
