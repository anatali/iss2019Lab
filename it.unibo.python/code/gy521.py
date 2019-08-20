"""
gy521.py
---------------------------------------------------
C:\Didattica2018Work\iss2019Lab\it.unibo.python\code
python gy521.py

Acquire data from gyroscope  
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt
import math as math

brokerAddr="localhost"
duration  = 15
gyrox         = []
gyroy         = []
gyroz         = []
gyror     	  = []
accelx        = []
accely        = []
accelz        = []
accelr     	  = []

counter   = 0 
maxnum    = 20
endOfJob  = False
dt        = 0
startTime = 0
angle     = 0.0

def on_message(client, userdata, message) :   #define callback
	global counter, maxnum, endOfJob, x, y, z, r, startTime, angle
	now     = time.time() 
	elapsed = now-startTime
	#print( "now=" , now, "elapsed=",  elapsed)
	startTime = now
	counter = counter + 1
	#msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
	evMsg = str( message.payload.decode("utf-8")  )
	msgitems = evMsg.split(",")
	msgType  = msgitems[4].split('(')[1]    #TYPE
	#print("evMsg=", evMsg, "msgType=", msgType )
	vx       = msgitems[5]					#X
	vy       = msgitems[6]					#Y
	vz       = msgitems[7].split(')')[0]	#Z	
	if msgType == "gyro" :
		handleGyroData( vx,vy,vz,elapsed )	   
	if msgType == "acceleromenter" :
		handleAccelData( vx,vy,vz,elapsed )	   
 	
	if counter >= maxnum :
 		client.disconnect()

        
def handleGyroData( x,y,z,elapsed ) :
	global counter, plt, maxnum
	gyrox.append( x)         
	gyroy.append( y )
	gyroz.append( z )
	da    = abs(z) * elapsed
	angle = angle + da
	r.append( angle )
	print("z=", ("%.3f" % z), "angle=", ("%.3f" % angle), "counter=", counter)
	#print("elapsed=", ("%.2f" % elapsed), "da=", ("%.2f" % da) ) 	
	if counter >= maxnum :
		plt.plot(gyrox, color='red')
		plt.plot(gyroy, color='green')
		plt.plot(gyroz, color='blue')
		plt.plot(gyror, color='black')
		plt.show()
	       
def handleAccelData( x,y,z,elapsed ) :
	global counter, plt, maxnum
	print("handleAccelData x=", x, "counter=",counter, "maxnum=", maxnum, "pi=", math.pi  )
	accelx.append( x ) 
	accely.append( y )
	accelz.append( z )
	"""
	Roll  = math.atan2(y, z) * 180.0/math.pi;
	Pitch = math.atan2(-x, math.sqrt(y*y + z*z)) * 180.0/math.pi;
	accelr.append( Roll )
	print("z=", ("%.3f" % z), "Pitch=", ("%.3f" % Pitch), "Roll=", ("%.3f" % Roll))
	"""
	if counter >= maxnum :
		plt.plot(accelx, color='red')
		plt.plot(accelx, color='green')
		plt.plot(accelx, color='blue')
		plt.plot(accelr, color='black')
		plt.show()
        
plt.style.use("classic")
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe
startTime     = time.time() 
#print( "startTime=" , ti
print( "startTime=" , startTime )
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    