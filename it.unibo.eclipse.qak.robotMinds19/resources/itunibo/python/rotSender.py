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

client= paho.Client("utilGY521")      
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)

time.sleep(1)
sendMsg( client, 'l') 
time.sleep(2)
sendMsg( client, 'r')
time.sleep(2)
sendMsg( client, 'l')
time.sleep(2)
sendMsg( client, 'r')

sendMsg( client, 'endofjob')

"""
"""
print( "bye " )