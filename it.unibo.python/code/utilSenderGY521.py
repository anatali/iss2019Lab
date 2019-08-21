"""
utilSenderGY521.py
python utilSenderGY521.py
"""

#!/usr/bin/python
import smbus
import math
import time
import paho.mqtt.client as paho
from mpu6050 import mpu6050

brokerAddr ="192.168.1.7"
maxnum     = 10
count      = 0

sensor = mpu6050(0x68)
  
def sendMsg( client,x,y,z, sensorType ) :
	global count
	count = count + 1
	#msg(sensor,event,gyroSender,none,sensor(TYPE,X,Y,Z),MSGNUM)	  				
	template = "msg(g521,event,rasp,none,g521({0},{1},{2},{3}),{4})"
	msgout   = template.format(sensorType, x,y,z,count)
	print("SEND-MQTT: " + msgout )
	client.publish("unibo/qak/events", msgout, 1, retain=False);		


def emitGyro(client) :
	x = read_word_2c(0x43)  / 131.0 #scaled
	y = read_word_2c(0x45)  / 131.0
	z = read_word_2c(0x47)  / 131.0 	
	#print "x: ", ("%5d" % x) , "y: ", ("%5d" % y) , "z: ", ("%5d" % z) 
	sendMsg(client,x,y,z,"gyro" )

def emitAccel(client) :	
	global sensor
	scale = 16384.0
	accelerometer_data = sensor.get_accel_data()
	#x = read_word_2c(0x3b) / scale
	#y = read_word_2c(0x3d) / scale	
	#z = read_word_2c(0x3f) / scale	
	print accelerometer_data 	 
 	#sendMsg(client,x,y,z,"accel" )

def doGyro() :
	print "Gyroscope"
	print "--------"	
	for i in range(1, maxnum) :
		emitGyro(client) 
		time.sleep(0.1)		#10 data/sec

def doAccel() :
	print "Accelerometer "
	print "---------------------"
	for i in range(1, maxnum) :
		emitAccel(client) 
		time.sleep(0.1)		#10 data/sec

client= paho.Client("gyroSender")      
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
startTime     = time.time() 
#print( "startTime=" , time.localtime( startTime ) )
print( "startTime=" , startTime )

##doGyro()
doAccel()

 
