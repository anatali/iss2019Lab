"""
rotTune.py

SET/ADD enable_uart = 1 in boot/config.txt
OR BETTER
enable serial via raspi-config	(Advanced Serial Setting)
sudo apt-get install python-serial
python -m serial.tools.list_ports
"""
import time
import paho.mqtt.client as paho
import serial

brokerAddr="192.168.1.7"
goon      = True
startTime = 0
dt        = 1
angle     = 0.0

ser = serial.Serial(
	port='/dev/ttyUSB0', 
	baudrate=115200,             
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=1 )

def sendDoneMsg( client  ) :
	msgout = "msg( gyro90done,event,gyro90,none,gyro90done,1 )"
	client.publish("unibo/qak/events", msgout, 0, retain=False);		


def on_message(client, userdata, message) :  
	global ser
	evMsg = str( message.payload.decode("utf-8")  )
	#msg(rotationCmd,event, SENDER, none, rotationCmd(CMD),MSGNUM)
	print("evMsg=", evMsg )
	msgitems = evMsg.split(",")
	CMD      = msgitems[4].split('(')[1].split(')')[0] 
	print( "CMD=", CMD )
	if CMD == 'r' :
		ser.write('r\n')           		
	if CMD == 'l' :
		ser.write('l\n')           
	if CMD == 'z' :
		ser.write('z\n')           
	if CMD == 'x' :
		ser.write('x\n')           
	
 
def testSerial() :
	print("testSerial r") 
	#ser = serial.Serial('/dev/ttyUSB0', baudrate=115200, )  # open serial port ttyAMA0
	print(ser.name)         # check which port was really use
	ser.flushInput()
	linein = ser.readline()     # read a string
	print("input="+linein)	
"""	
	time.sleep(1)
	print("write r") 
	ser.write('r\n')          # write a string
	time.sleep(1)
	print("write x") 
	ser.write('x\n')          # write a string
	time.sleep(1)
	ser.close()             # close port
"""

testSerial()



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
