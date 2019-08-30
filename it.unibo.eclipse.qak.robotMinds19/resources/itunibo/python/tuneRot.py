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

def storeData( msg ) :	
	dataFile.write( msg )
	dataFile.flush()

def doTuneRight() :
	v = 0
	cmd = 0
	while v == 0 :
		userNumber = input('doTuneRight. Give me a float number (e.g. 0.515) : ')
		cmd = 'cr'+ str(userNumber)
		print( "tune r : " +cmd) 
		ser.write( bytes( 'cr0.9' ) )
		time.sleep(1)
		print( "testTune r " )
		ser.write( 'r' )
		v = input('Is it ok? (0/1) : ')
	storeData( "ROT RIGHT="+ str(cmd)  +"\n" )
	
def doTuneLeft() :
	v = 0
	cmd = 0
	while v == 0 :
		userNumber = input('doTuneLeft. Give me a float number (e.g. 0.515) : ')
		cmd = 'cl'+ str(userNumber)
		print( "tune l : " +cmd) 
		ser.write( bytes( cmd )  )
		time.sleep(1)
		print( "testTune l " )
		ser.write(  'l'  )
		v = input('Is it ok? (0/1) : ')
	storeData( "ROT LEFT="+ str(cmd) +"\n" )

def testTune() :
	v = 0
	cmd = 0
	while v == 0 :
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
		time.sleep(1)
		v = input('testTune OK? (0/1) : ')

open('configRot.txt', 'w').close()	#clean the file
dataFile  = open("configRot.txt", "a")
  
doTuneRight()
doTuneLeft()
print( "close serial " )
ser.close()
#testTune()

time.sleep(1)	  
print( "bye " )