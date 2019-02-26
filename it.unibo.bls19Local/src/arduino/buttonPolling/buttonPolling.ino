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
  Serial.println( "buttonPolling/buttonPolling.ino"  );
  Serial.println( "--------------------------------------"  );
  Serial.println( "START@"  );
  initButton();
  initLed();
}

/*
 * When the button is PUSHED, the led is ON
 */
void loop(){
  int v = digitalRead( pinButton );
  if( v > 0 ) Serial.println( "0"   ); else Serial.println( "1"  );
  //if( v > 0 ) digitalWrite( pinLed,  v );
  delay(250);
}
