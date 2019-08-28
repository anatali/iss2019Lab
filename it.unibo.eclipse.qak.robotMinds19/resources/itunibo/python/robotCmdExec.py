"""
robotCmdExec.py

GOAL: 			emit g521 qak-events for gyro or accel
MSG-FORMAT:  	msg(g521,event,rasp,none,g521(gyro,X,Y,Z),39)  X,Y,Z float

USAGE: 			python g521emitter.py
"""

import math
import time
import datetime
import paho.mqtt.client as paho
import serial
from mympu6050 import mympu6050

brokerAddr ="192.168.1.7"
tsleep     = 0.1
maxnum     = 20  
count      = 0
startTime  = 0
angle      = 0.0 
duration   = 5
goon       = True

lowAngle  = 87.5
highAngle = 92.5       

sensor = mympu6050(0x68)

open('dataRot.txt', 'w').close()	#clean the file
dataFile  = open("dataRot.txt", "a")

ser = serial.Serial(
	port='/dev/ttyUSB0', 
	baudrate=115200,             
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=10 )

def storeData( msg ) :	
	dataFile.write( msg )
	dataFile.flush()
	
def on_message(client, userdata, message) :  
	global angle, goon 
	evMsg = str( message.payload.decode("utf-8")  )
	#msg(rotationCmd,event, SENDER, none, rotationCmd(CMD),MSGNUM)
	msgitems = evMsg.split(",")
	if msgitems[0] == "msg(g521" :
		return
	#print "evMsg=", evMsg 
	angle = 0.0
	CMD  = msgitems[4].split('(')[1].split(')')[0] 
	if CMD == "endofjob" :
		#print("ENDOFJOB ...")
		goon = False
		#sendMsg(client,'endofjob',0,0,0,0)  #propagate for plot
		return
	#print "CMD=", CMD  
	if (CMD == 'w') or (CMD == 's') or (CMD == 'h')  :
		ser.write( CMD )		#EXECUTE THE COMMAND
 		return
	if CMD == 'r' or CMD == 'l'  :
		doGyro(CMD)
	if  CMD == 'x' or CMD == 'z' :
		robotStep(CMD)

def sendMsg( client,x,y,z,angle,sensorType ) :
	global count 
	count = count + 1
	#msg(sensor,event,gyroSender,none,sensor(TYPE,X,Y,Z,ANGLE),MSGNUM)	  				
	template = "msg(g521,event,rasp,none,g521({0},{1},{2},{3},{4}),{5})"
	msgout   = template.format(sensorType, x,y,z,angle,count)
	#print "SEND-MQTT: " + msgout  
	client.publish("unibo/qak/events", msgout, 0, retain=False);		

def doGyro(CMD) :	 
	global angle , dataFile, lowAngle, highAngle
	ser.write( CMD )		#EXECUTE THE COMMAND
	da = evalAngle(CMD)
	da = evalAngle(CMD)
	while da > 0.7 :
		da = evalAngle(CMD)
	print "FIRST STEP DONE CMD=", CMD , " DONE angle=" , angle	
	storeData( "FIRST STEP DONE CMD={0} ANGLE={1} \n".format(CMD,("%.3f" % angle)) )
	if   angle  < lowAngle or   angle  > highAngle :
		compensate( CMD )
	angle = 0.0

def evalAngle( CMD  ) :		
	global sensor, angle, tsleep, client
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = abs( data['z']  )
	sendMsg( client,x,y,z,angle, "gyro" ) 	#events arrive at this app too!!!
	### ACCUMULATE ### 
	da    = (z * tsleep)
 	angle =  angle + da 
	print "evalAngle:", angle , " z=" , z , " CMD=" , CMD, "da=" , da
	storeData( "z={0} angle={1} da={2}\n".format(("%.3f" % z), ("%.3f" % angle), ("%.3f" % da)) )
	time.sleep(tsleep) 
	return da
 
def compensate( CMD ) :
	global angle, dataFile, lowAngle, highAngle
 	print " --- compensate START:" , CMD, " angle=" , angle
	template = "COMPENSATE START CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
 	dataFile.flush()
 	da = 0.0
 	if CMD == 'r' :	
 		if  angle  < lowAngle :
 			da = robotStep('x' )
 			angle = angle + da		
 		if  angle  > highAngle :
 			da = robotStep('z' )
 			angle = angle - da
  	if CMD == 'l' :	
 		if angle < lowAngle :
 			da = robotStep('z'  )
 			angle = angle + da
  		if angle > highAngle :
 			robotStep('x'  )
 			angle = angle - da
 	print " --- compensate END :" , CMD, " da=", da, " angle=" , angle
	storeData( "COMPENSATE END CMD={0} ANGLE={1} \n".format(CMD, angle) )

def robotStep( CMD  ) :
	global sensor, angle, tsleep, ser
 	ser.write( CMD )
	time.sleep(tsleep)
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = abs( data['z'] )
	da = (z * tsleep)  
	time.sleep(tsleep)
	return da

client= paho.Client("moveRobot")  
client.on_message=on_message            # Bind function to callback    

client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
startTime     = time.time() 
#print( "startTime=" , time.localtime( startTime ) )
print( "startTime=" , startTime )

print("subscribing to unibo/qak/events")
client.subscribe("unibo/qak/events")      #subscribe

print("collecting values; please wait ..." )
client.loop_start()             #start loop to process received messages
dataFile.write("START JOB robotCmdExec at " + str( datetime.datetime.now() ) + " \n")
while goon :
	time.sleep(5)
	#print("RESUME ...")
dataFile.write("STOP JOB robotCmdExec at " + str( datetime.datetime.now() ) + " \n")

msgout = "msg(pythonEnd,event,rasp,none,pythonEnd,1)"
client.publish("unibo/qak/events", msgout, 0, retain=False);		

dataFile.close()
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    


#sudo date -s "Wed Aug 28 10:08:00 UTC 2019"