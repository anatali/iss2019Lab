"""
testTuneRot.py
"""
import time
import serial

ser = serial.Serial(
	port='/dev/ttyUSB0', 
	baudrate=115200,             
	parity=serial.PARITY_NONE,
	stopbits=serial.STOPBITS_ONE,
	bytesize=serial.EIGHTBITS,
	timeout=1 )



def testTune() :
	time.sleep(2)
	print( "testTune do r " )
	ser.write('r')
	time.sleep(2)
	print( "testTune do l " )
	ser.write('l')
	time.sleep(2)
	print( "testTune do l " )
	ser.write('l')
	time.sleep(2)
	print( "testTune do r " )
	ser.write('r')
 

testTune()

time.sleep(1)	  
print( "bye " )