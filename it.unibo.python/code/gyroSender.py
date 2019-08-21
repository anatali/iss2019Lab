"""
gyroSender.py
python gyroSender.py
"""

#!/usr/bin/python
import smbus
import math
import time
import paho.mqtt.client as paho

brokerAddr ="192.168.1.7"
maxnum     = 50
count      = 0

# Register
power_mgmt_1 = 0x6b
power_mgmt_2 = 0x6c

bus     = smbus.SMBus(1)    # 0 = /dev/i2c-0 (port I2C0), 1 = /dev/i2c-1 (port I2C1)
address = int('0x68',16)

def read_byte(reg):
	return bus.read_byte_data(address, reg)

def read_word(reg):
	h = bus.read_byte_data(address, reg)
	l = bus.read_byte_data(address, reg+1)
	value = (h << 8) + l
	return value

def read_word_2c(reg):
	val = read_word(reg)
	if (val >= 0x8000):
		return -((65535 - val) + 1)
	else:
		return val
		
def dist(a,b):
	return math.sqrt((a*a)+(b*b))
	
def get_y_rotation(x,y,z):
	radians = math.atan2(x, dist(y,z))
	return -math.degrees(radians)
	
def get_x_rotation(x,y,z):
	radians = math.atan2(y, dist(y,z))
	bus.write_byte_data(address, power_mgmt_1, 0)

def sendMsg( client,x,y,z ) :
	global count
	count = count + 1
	#msg(sensor,event,gyroSender,none,sensor(TYPE,X,Y,Z),MSGNUM)	  				
	template = "msg( g521Sensor, event, gyroSender, none, g521Sensor(g521,{0},{1},{2}),{3} )"
	msgout   = template.format(x,y,z,count)
	print("SEND-MQTT: " + msgout )
	client.publish(   "unibo/qak/events", msgout, 1, retain=False);		


def emitGyro(client) :
	x = read_word_2c(0x43)  / 131.0 #scaled
	y = read_word_2c(0x45)  / 131.0
	z = read_word_2c(0x47)  / 131.0 	
	#print "x: ", ("%5d" % x) , "y: ", ("%5d" % y) , "z: ", ("%5d" % z) 
	sendMsg(client,x,y,z )

def emitAccel() :
	print "Accelerometer "
	print "---------------------"
	
	acceleration_xout = read_word_2c(0x3b)
	acceleration_yout = read_word_2c(0x3d)
	acceleration_zout = read_word_2c(0x3f)
	 
	acceleration_xout_scaled = acceleration_xout / 16384.0
	acceleration_yout_scaled = acceleration_yout / 16384.0
	acceleration_zout_scaled = acceleration_zout / 16384.0


	print "acceleration_xout: ", ("%6d" % acceleration_xout), " scaled: ", acceleration_xout_scaled
	print "acceleration_yout: ", ("%6d" % acceleration_yout), " scaled: ", acceleration_yout_scaled
	print "acceleration_zout: ", ("%6d" % acceleration_zout), " scaled: ", acceleration_zout_scaled


client= paho.Client("gyroSender")      
client.connect(brokerAddr)              #connect
print("connected to broker ", brokerAddr)
startTime     = time.time() 
#print( "startTime=" , time.localtime( startTime ) )
print( "startTime=" , startTime )

print "Gyroscope"
print "--------"	
for i in range(1, maxnum) :
	emitGyro(client) 
	time.sleep(0.1)		#10 data/sec

""" 
print "X Rotation: " , get_x_rotation(acceleration_xout_scaled, acceleration_yout_scaled, acceleration_zout_scaled)
print "Y Rotation: " , get_y_rotation(acceleration_xout_scaled, acceleration_yout_scaled, acceleration_zout_scaled) 
client.loop_start()             #start loop to process data
time.sleep(maxnum)
client.disconnect()             #disconnect
print("bye")
client.loop_stop()              #stop loop        
"""
