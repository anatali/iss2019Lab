#!/usr/bin/python
"""
moveRobot.py

GOAL: 			emit g521 qak-events for gyro or accel
MSG-FORMAT:  	msg(g521,event,rasp,none,g521(gyro,X,Y,Z),39)  X,Y,Z float

USAGE: 			python g521emitter.py
"""

#### import smbus
import math
import time
import datetime
import paho.mqtt.client as paho
import serial
from mympu6050 import mympu6050

brokerAddr ="192.168.1.7"
tsleep     = 0.1
ndatasec   = 1.0 / tsleep
emitTime   = 3  				#sec
maxnum     = 20 				#int(ndatasec)*emitTime
count      = 0
startTime  = 0
oldangle    = 0.0
angle      = 0.0 
duration   = 20

lowAngle  = 88.0
highAngle = 92.5        

sensor = mympu6050(0x68)

open('dataRot.txt', 'w').close()	#clean the file
dataFile  = open("dataRot.txt", "a")

def storeData( msg ) :
	dataFile.write( msg )
	dataFile.flush()
	
def on_message(client, userdata, message) :  
	global angle 
	evMsg = str( message.payload.decode("utf-8")  )
	#msg(rotationCmd,event, SENDER, none, rotationCmd(CMD),MSGNUM)
	msgitems = evMsg.split(",")
	if msgitems[0] == "msg(g521" :
		return
	if msgitems[0] == "endofjob" :
		sendMsg(client,'endofjob',0,0,0,0)
		return
	print "evMsg=", evMsg 
	CMD      = msgitems[4].split('(')[1].split(')')[0] 
	#print "CMD=", CMD 
	angle = 0.0
	if CMD == 'r' or CMD == 'l'  :
		doGyro(CMD)
	if  CMD == 'x' or CMD == 'z' :
		doGyroStep(CMD)
	

ser = serial.Serial(
	port='/dev/ttyUSB0', 
	baudrate=115200,             
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=1 )

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
	ser.write(CMD+'\n')		#EXECUTE THE COMMAND
	da = evalAngleDirect(CMD)
	oldangle = angle
	da = evalAngleDirect(CMD)
	#da = abs( angle - oldangle )
	while da > 0.7 :
		oldangle = angle
		da = evalAngleDirect(CMD)
		#da = abs( angle - oldangle )	 
	print "FIRST STEP CMD=", CMD , " DONE angle=" , angle	
	template = "FIRST STEP DONE CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD,angle) )
	dataFile.flush()
	if  abs(angle) < lowAngle or  abs(angle) > highAngle :
		compensate( CMD )
	angle = 0.0
	
def evalAngleDirect( CMD  ) :		
	global sensor, angle, tsleep, client
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = data['z']  
	if  z < 0 :
		z = -z
	sendMsg( client,x,y,z,angle, "gyro" ) 	#events arrive at this app too!!!
	### ACCUMULATE ### 
	da    = (z * tsleep)
 	angle =  angle + da 
	print "evalAngleDirect:", angle , " z=" , z , " CMD=" , CMD, "da=" , da
	template = "z={0} ANGLE={1} da={2}\n"
	dataFile.write( template.format(z, angle, da) )
	dataFile.flush()
	time.sleep(tsleep) 
	return da
 
  
def robotStep( CMD  ) :
	ser.write( CMD )
	time.sleep(tsleep)
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = abs( data['z'] )
	da = (z * tsleep)  
	angle = angle + da 
 	print "robotStep:", angle , " z=" , z , " CMD=" , CMD, "da=" , da
	template = "z={0} ANGLE={1} da={2}\n"
	dataFile.write( template.format(z, angle, da) )
	dataFile.flush()
	

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
dataFile.write("START JOB " + str( datetime.datetime.now() ) + " \n")
time.sleep(duration)
dataFile.close()
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    

#doAccel()
#doGyro()

"""
def doGyroStep( CMD ) :
	global angle 	
	evalAngle(CMD)
	startangle = angle
	ser.write(CMD+'\n')
	evalAngle(CMD)
	print "doGyroStep angle=" , angle , " angle corrected= ", angle-startangle	

	
def compensate( CMD ) :
	global angle, dataFile, lowAngle, highAngle
 	print " ----------------------------------- compensate START:" , CMD, " angle=" , angle
	template = "COMPENSATE START CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
 	dataFile.flush()
 	if CMD == 'r' :	
 		if  angle  < lowAngle :
 			robotStep('x\n' )		
 		if  angle  > highAngle :
 			robotStep('z\n' )
  	if CMD == 'l' :	
 		if angle < lowAngle :
 			robotStep('z\n'  )
  		if angle > highAngle :
 			robotStep('x\n'  )
 	print " ----------------------------------- compensate END :" , CMD, " angle=" , angle
	template = "COMPENSATE END CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
 	dataFile.flush()
	print "COMPENSATE	 angle=" , angle	


 
def evalAngle(CMD, mode="direct") :		# mode = "direct" or "compensate"
	global angle, tsleep, client
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = data['z']
	da = 0.0
	sendMsg( client,x,y,z,angle, "gyro" ) 	#events arrive at this app too!!!
	### ACCUMULATE ### 
	if ( CMD == 'r' and z < 0  ) :
		if( z < 0 ) :
			da    = abs(z) * tsleep
		else :
			da    = - (z * tsleep)
		if mode == "compensate" :
			da = -da
	if ( CMD == 'l' and z > 0 ) :
		da    = z * tsleep		
		if mode == "compensate" :
			da = -da
	angle = angle + da 
	print "evalAngle:", angle , " z=" , z , " CMD=" , CMD, "mode=" , mode, "da=" , da
	template = "z={0} ANGLE={1} mode={2} da={3}\n"
	dataFile.write( template.format(z, angle,mode,da) )
	dataFile.flush()
	time.sleep(tsleep) 

def compensateOld( CMD ) :
	global angle, dataFile, lowAngle, highAngle
 	print " ----------------------------------- compensate START:" , CMD, " angle=" , angle
	template = "COMPENSATE START CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
 	dataFile.flush()
 	if CMD == 'r' :	
 		if  angle  < lowAngle :
 			ser.write('x\n')
 			evalAngleDirect( 'r'  )
 		if  angle  > highAngle :
 			ser.write('z\n')
 			evalAngleDirect( 'l' )
  	if CMD == 'l' :	
 		if angle < lowAngle :
 			ser.write('z\n')
 			evalAngleDirect( 'l' )
  		if angle > highAngle :
 			ser.write('x\n')	
 			evalAngleDirect('r' )
 	print " ----------------------------------- compensate END :" , CMD, " angle=" , angle
	template = "COMPENSATE END CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
 	dataFile.flush()
	print "COMPENSATE	 angle=" , angle	
"""