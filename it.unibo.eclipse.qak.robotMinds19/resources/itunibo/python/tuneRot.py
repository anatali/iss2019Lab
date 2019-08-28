"""
tuneRot.py
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

def doTuneRight() :
	v = 0
	while v == 0 :
		userNumber = input('doTuneRight. Give me a float number (e.g. 0.515) : ')
		cmd = 'cr'+ str(userNumber)
		print( "tune r : " +cmd) 
		ser.write( cmd )
		time.sleep(1)
		print( "testTune r " )
		ser.write('r')
		v = input('Is it ok? (0/1) : ')

def doTuneLeft() :
	v = 0
	while v == 0 :
		userNumber = input('doTuneLeft. Give me a float number (e.g. 0.515) : ')
		cmd = 'cl'+ str(userNumber)
		print( "tune l : " +cmd) 
		ser.write( cmd )
		time.sleep(1)
		print( "testTune l " )
		ser.write('l')
		v = input('Is it ok? (0/1) : ')

  
doTuneRight()
doTuneLeft()

time.sleep(1)	  
print( "bye " )