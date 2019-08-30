"""
robotCmdExec.py

GOAL: 			emit g521 qak-events for gyro or accel
MSG-FORMAT:  	msg(g521,event,rasp,none,g521(gyro,X,Y,Z),39)  X,Y,Z float

USAGE: 			python g521emitter.py

pip install serial
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
dastep     = 1.7 		#dastep estimated
damin      = 0.01


lowAngle  = 89.0
highAngle = 91.0       

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
	if CMD == "wstep" :
		ser.write( 'w' )
		time.sleep(0.8)
		ser.write( 'h' )
		return
	if CMD == "endofsender" :   #emitted by rotSender.py
		print("ENDOFJOB ...")
		goon = False
		sendMsg(client,0,0,0,0,'endofpythonexec')  #propagate for plot
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
	template = "msg(g521,event,rasp,none,g521({0},{1},{2},{3},{4}),{5})"
	msgout   = template.format(sensorType, x,y,z,("%.3f" % angle),count)
	#print "SEND-MQTT: " + msgout  
	client.publish("unibo/qak/events", msgout, 0, retain=False);		

def doGyro(CMD) :	 
	global angle 
	#da = evalAngle(CMD)
	ser.write( CMD )		#EXECUTE THE COMMAND (r,l)
	da = evalAngle(CMD)
	while da > damin :
		da = evalAngle(CMD)
	print( "FIRST STEP DONE CMD=", CMD , " DONE angle=" , angle	)
	storeData( "FIRST STEP DONE CMD={0} ANGLE={1} \n".format(CMD,("%.3f" % angle)) )
	
	if angle < lowAngle or  angle > highAngle :
		compensate( CMD )

	angle = 0.0

def evalAngle( CMD  ) :		
	global angle 
	time.sleep(tsleep) 
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = abs( data['z']  )
	sendMsg( client,x,y,z,angle, "robotCmdExec" ) 	#events arrive at this app too!!!
	### ACCUMULATE ### 
	da    = (z * tsleep)
	angle =  angle + da 
	print( "evalAngle:", ("%.3f" %angle) , " z=" , ("%.3f" %z)  , " CMD=" , CMD, "da=" , ("%.3f" %da)  )
	storeData( "z={0} angle={1} da={2}\n".format(("%.3f" % z), ("%.3f" % angle), ("%.3f" % da)) )
	return da

def compensate( CMD ) :
	global angle 
	print( " --- compensate START:" , CMD, " angle=" , angle )
	template = "COMPENSATE START CMD={0} ANGLE={1} \n"
	dataFile.write( template.format(CMD, angle) )
	dataFile.flush()
	while   angle < lowAngle or  angle > highAngle :
		da = docompensate( CMD )
		print( " --- compensate WORK " , CMD, " da=", ("%.3f" %da), " angle=" , ("%.3f" %angle) )
		time.sleep(0.1)
	print( " --- compensate END :" , CMD, " angle=" , ("%.3f" %angle) )
	sendMsg( client,0,0,0,angle, "robotCmdExec" ) 		# x,y,z not significative
	storeData( "COMPENSATE END CMD={0} ANGLE={1} \n".format(CMD, ("%.3f" %angle)) )
	 
def docompensate( CMD ) :
	global angle 
	if CMD == 'r' :
			if angle  < lowAngle :
 				da    = robotStep('x' )
 				angle = angle + da		
			if  angle  > highAngle :
 				da    = robotStep('z' )
 				angle = angle - da
	if CMD == 'l' :
 			if angle  < lowAngle :
 				da    = robotStep('z')
 				angle = angle + da
  			if angle > highAngle :
 				da    = robotStep('x'  )
 				angle = angle - da
 	return da
	
def robotStep( CMD  ) :
	#global angle 
	ser.write( bytes( CMD ) )
	time.sleep(tsleep)
	data = sensor.get_gyro_data()
	x  = data['x']
	y  = data['y']
	z  = abs( data['z'] )
	da = (z * tsleep)  
	if da > dastep :	#dastep estimated
		return da
 	else :
 		return dastep

def execFromInput() :
	local = True
	while local :
		v = input('0 => wstep 1 => r | 2 => l  | 3 => x | 4 => z  | 5 => remote : ')
		print( "execFromInput v=" + str( v ) + " " + str( v == 2 ) ) 
		if v == 0 :
			ser.write( bytes( 'w' ) )
			time.sleep(0.8)
			ser.write( bytes( 'h' ) )
		if v == 1 :
 			doGyro( 'r' )
 		if v == 2 :
 			doGyro( 'l' )
 		if v == 3 :
			ser.write( bytes( 'x' ) )
		if v == 4 :
			ser.write( bytes( 'z' ) )
		if v == 5 :
			local = False

		
		
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

"""
-----------------------------------------------------------
first accepts user commands from the console
afterwars waits for remote commands
-----------------------------------------------------------
"""
execFromInput() 

while goon :
	time.sleep(5)
	#print("RESUME ...")
dataFile.write("STOP JOB robotCmdExec at " + str( datetime.datetime.now() ) + " \n")

msgout = "msg(pythonEnd,event,rasp,none,pythonEnd,1)"
client.publish("unibo/qak/events", msgout, 0, retain=False); #handled by g521support in robotmind.qak

dataFile.close()
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop    


#sudo date -s "Wed Aug 28 10:08:00 UTC 2019"


"""
"""