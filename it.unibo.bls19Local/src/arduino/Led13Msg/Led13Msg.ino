/*
===========================================================
 project it.unibo.arduino.intro Led13Msg/Led13Msg.ino ARDUINO UNO
 Pin 13 has a internal LED connected on  ARDUINO UNO. 
===========================================================
 */
int ledPin   = 13; 
int count    = 1;
boolean on   = false;

void setup(){  
  Serial.begin(9600);  
  Serial.println( "--------------------------------------"  );
  Serial.println( "project it.unibo.bls19Local"  );
  Serial.println( "arduino/Led13Msg/Led13Msg.ino"  );
  Serial.println( "--------------------------------------"  );
  Serial.println( "START@"  );
  configure();
}
void configure(){
  pinMode( ledPin, OUTPUT );
  turnOff();  
}
/* -----------------------------
LED primitives
-------------------------------- */
void turnOn(){
  digitalWrite(ledPin, HIGH);
  on = true;
  emitLedState();
}
void turnOff(){
  digitalWrite(ledPin, LOW);
  on = false;
  emitLedState();
}
void emitLedState(){
   //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
  Serial.println("msg( info, event, arduino, none, ledstate(" + String(on) + "), " + String(count++) + " )\n" );
}
/* -----------------------------
Blinck the led (non primtive)
-------------------------------- */
void blinkTheLed(){
    turnOn(); 
    delay(500);
    turnOff();  
    delay(500);
}
/* -----------------------------
Input handler
-------------------------------- */
void cmdHandler(){
   int v = Serial.read();   //NO BLOCKING
   //if( v > 0 ) Serial.println("arduinio input=" + String(v)  );
   if( v != - 1 && v != 13 && v != 10 ){
     boolean isPressed = ( v == 49 ) ; //48 is '0' 49 is '1'
     if( isPressed ) turnOn();
     else turnOff();
   }
}
void loop(){  
  cmdHandler();
}
