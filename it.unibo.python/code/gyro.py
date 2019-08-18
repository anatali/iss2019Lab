"""
gyro.py
---------------------------------------------------
C:\Didattica2018Work\iss2019Lab\it.unibo.python\code
python gyro.py

Acquire data from gyroscope and integrate them to get a rotation angle
"""
import time
import paho.mqtt.client as paho
import matplotlib.pyplot as plt

brokerAddr="192.168.1.6"
duration = 10
x        = []
y        = []
z        = []
r        = []
counter  = 0 
maxnum   = 10
endOfJob = False
dt       = 0
startTime = 0
angle     = 0.0

def on_message(client, userdata, message) :   #define callback
	global counter, maxnum, endOfJob, x, y, z, r, startTime, angle
	if endOfJob  :
 		return
	now     = time.time() 
	elapsed = now-startTime
	#print( "now=" , now, "elapsed=",  elapsed)
	startTime = now
	counter = counter + 1
	#msg(androidSensor,event,android,none,androidSensor(TYPE,X,Y,Z),MSGNUM)
	evMsg = str( message.payload.decode("utf-8")  )
	#print("evMsg=", evMsg )
	msgitems = evMsg.split(",")
	x.append( float( msgitems[5] ) )
	v = float( msgitems[6] )
	y.append( v )
	v = float( msgitems[7].split(')')[0] )
	z.append( v )
	
	da    = abs(v) * elapsed
	angle = angle + da
	print("v=", v, "v=", v, "elapsed=",  elapsed, "da=", da ) 
	r.append( angle )
	
	if counter	> maxnum  :
		plt.plot(x, color='red')
		plt.plot(y, color='green')
		plt.plot(z, color='blue')
		plt.plot(r, color='black')
		plt.show()
		endOfJob = True  		 	
    
plt.style.use("classic")
client= paho.Client("receiver")      
client.on_message=on_message            # Bind function to callback
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe
startTime     = time.time() 
#print( "startTime=" , time.localtime( startTime ) )
print( "startTime=" , startTime )
client.loop_start()             #start loop to process received messages
time.sleep(duration)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop        


#https://towardsdatascience.com/data-visualization-using-matplotlib-16f1aae5ce70
#https://matplotlib.org/users/pyplot_tutorial.html