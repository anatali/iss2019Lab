"""
tuneRotOnPC.py

USAGE: 			python tuneRotOnPC.py

pip install pyserial
"""

import math
import time
import datetime
import serial
 
 
tsleep     = 0.1
maxnum     = 20  
count      = 0
startTime  = 0
angle      = 0.0 
duration   = 5
goon       = True

lowAngle  = 87.5
highAngle = 92.5       

 
#open('dataRot.txt', 'w').close()	#clean the file
#dataFile  = open("dataRot.txt", "a")

ser = serial.Serial(
	port='COM6', 
	baudrate=115200,             
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=10 )


def doTune( CMD ) :
	userNumber = input('input a float:  0.65 ... ')
	cmd = CMD + str(userNumber)
	print( "tune : " +cmd) 
	ser.write( bytes(cmd, 'utf-8')  )

def execFromInput() :
	local = True
	while local :
		v = input('0 => wstep 1 => r | 2 => l  | 3 => x | 4 => z  | 5 => remote : ')
		print( "execFromInput v=" + v + " " + str( v == '2' ) ) 
		if v == '0' :
			ser.write( bytes('w', 'utf-8') )
			time.sleep(0.8)
			ser.write( bytes('h', 'utf-8') )
		if  v == '1' :
			ser.write( bytes('r', 'utf-8') )
		if  v == '2' :
			print( "execFromInput l "  ) 
			ser.write( bytes('l', 'utf-8') )
		if  v == '3' :
			ser.write( bytes('x', 'utf-8') )
		if  v == '4' :
			ser.write( bytes('zw', 'utf-8') )
		if  v == '5' :
			local = False
		time.sleep(2)
		
			

#doTune( "cr" )
doTune( "cl" )
execFromInput() 

 

#sudo date -s "Wed Aug 28 10:08:00 UTC 2019"