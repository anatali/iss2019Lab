/*
=======================================
 buttonPolling.ino
 project it.unibo.kotlinJava
 Input from Pin 3 (Button)
 ARDUINO UNO
 =======================================
 Arduino has internal PULLUP resistors to tie the input high.  
 Hook one end of the switch to the input (5V) and the other end to ground: 
 when we PUSH the switch the input goes LOW. 
 */
 
int pinButton = 3;   
int pinLed    = 13;  //internal led
boolean on    = false;
int count     = 1;

void initLed(){
   pinMode(pinLed, OUTPUT);  
}
void initButton(){
  pinMode(pinButton, INPUT); 
  digitalWrite(pinButton, HIGH);  //set PULLUP : PUSH=>0
}

void setup(){  
  Serial.begin(9600);
  Serial.println( "--------------------------------------"  );
  Serial.println( "project it.unibo.bls19Local"  );
  Serial.println( "bls2019/bls2019.ino"  );
  Serial.println( "--------------------------------------"  );
  Serial.println( "START@"  );
  initButton();
  initLed();
}


void turnOn(){
  digitalWrite(pinLed, HIGH);
  on = true;
  emitLedState();
}
void turnOff(){
  digitalWrite(pinLed, LOW);
  on = false;
  emitLedState();
}
void emitLedState(){
   //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
  Serial.println("msg( info, event, arduino, none, ledstate(" + String(on) + "), " + String(count++) + " )\n" );
}
void emitButtonState(int v){
   //msg( MSGID, MSGTYPE, SENDER, RECEIVER, CONTENT, SEQNUM )
  Serial.println("msg( info, event, arduino, none, buttonstate(" + String(v) + "), " + String(count++) + " )\n" );
}
void readAndExecCmd(){
   int v = Serial.read();   //NO BLOCKING
   //if( v > 0 ) Serial.println("arduinio input=" + String(v)  );
   if( v != - 1 && v != 13 && v != 10 ){
     boolean isPressed = ( v == 49 ) ; //48 is '0' 49 is '1'
     if( isPressed ) turnOn();
     else turnOff(); 
  }
}

void sendButtonState(){
  int v = digitalRead( pinButton );
  //if( v > 0 ) Serial.println( "0"   ); else Serial.println( "1"  );  
  emitButtonState(v);
}

void loop(){
  readAndExecCmd();
  sendButtonState();
  //if( v > 0 ) digitalWrite( pinLed,  v );
  delay(250);
}
