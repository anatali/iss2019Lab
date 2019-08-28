import paho.mqtt.client as paho
import time

"""
rotSender.py
"""
brokerAddr ="localhost"

def sendMsg( client,CMD ) :
	#msg(rotationCmd,event,gyroSender,none,rotationCmd(CMD),MSGNUM)	  
	msgout   = "msg(rotationCmd,event,source,none,rotationCmd("+ CMD +")," + "1)"
	print("SEND: " + msgout )
	client.publish("unibo/qak/events", msgout, 0, retain=False);

def doPath() :
	time.sleep(1)
	sendMsg( client, 'w') 
	time.sleep(1)
	sendMsg( client, 'h') 
	time.sleep(1)
	sendMsg( client, 'l') 
	time.sleep(2)
	sendMsg( client, 'l')
	time.sleep(2)
	sendMsg( client, 'w') 
	time.sleep(1)
	sendMsg( client, 'h') 
	time.sleep(1)
	sendMsg( client, 'r') 
	time.sleep(2)
	sendMsg( client, 'r') 

def doRotate() :
	sendMsg( client, 'l') 
	time.sleep(2)
	#sendMsg( client, 'showangle')
	sendMsg( client, 'r')
	time.sleep(2)

client= paho.Client("utilGY521")      
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)

#doRotate()

time.sleep(2)	 #give time to elaborate before ending
sendMsg( client, 'endofjob')
print( "bye " )