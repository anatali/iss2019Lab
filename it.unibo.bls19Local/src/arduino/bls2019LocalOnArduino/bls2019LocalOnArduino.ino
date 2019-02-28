/*
=======================================
 bls2019LocalOnArduino.ino
 project it.unibo.bls2019LocalOnArduino
 Input from Pin 3 (Button)
 ARDUINO UNO
 =======================================
 Arduino has internal PULLUP resistors to tie the input high.  
 Hook one end of the switch to the input (5V) and the other end to ground: 
 when we PUSH the switch the input goes LOW. 
 */
 
int pinButton = 3;   
int pinLed    = 13;  //internal led
int curState  = LOW;
int oldState  = LOW;

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
  Serial.println( "project it.unibo.bls2019Local"  );
  Serial.println( "bls2019LocalOnArduino.ino"  );
  Serial.println( "--------------------------------------"  );
  initButton();
  initLed();
}

void turnOn(){
  digitalWrite(pinLed, HIGH);
}
void turnOff(){
  digitalWrite(pinLed, LOW);
}

void doBlink(){
    turnOn();
    delay( 200 );
    turnOff(); 
    delay( 200 );
}

void readButton(){
   int vb = digitalRead( pinButton );
   if(  vb == LOW && oldState == HIGH ) { //something happens
      Serial.println("button clicked"  );
      curState    = 1 - curState; //commute
      delay(15);    //avoid bouncing
   }
   oldState = vb;
}


void loop(){
  readButton();
  if( curState == HIGH ) doBlink() ;
}
