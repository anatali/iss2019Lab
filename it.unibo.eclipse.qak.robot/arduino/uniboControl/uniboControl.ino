#include <Arduino.h>
#include <Wire.h>
#include <SoftwareSerial.h>
#include <MeMCore.h>

MeDCMotor motor_9(9);
MeDCMotor motor_10(10);
double angle_rad = PI/180.0;
double angle_deg = 180.0/PI;
void lookAtSonar();
void move(int direction, int speed);
double sonar;
int input;
int count;
MeUltrasonicSensor ultrasonic_3(3);
MeRGBLed rgbled_7(7, 7==7?2:4);
void remoteCmdExecutor();


/*
 * -----------------------------------
 * Line follower
 * -----------------------------------
 */
double stopFollow = true;
double sonarVal;
void lineFollow();
MeLineFollower linefollower_2(2);

void sonarDetect()
{
    sonarVal = ultrasonic_3.distanceCm();
    Serial.println(sonarVal);
    if((sonarVal) < (10)){
        move(1,0);
        stopFollow = true;
        //Serial.println("stopFollow line follow");
    }
}
void lineFollow()
{
    if( stopFollow == true ) return;
    if(((linefollower_2.readSensors())==(0))){
        move(1,200);
    }
    if(((linefollower_2.readSensors())==(1))){
        motor_9.run((9)==M1?-(0):(0));
        motor_10.run((10)==M1?-(150):(150));
    }
    if(((linefollower_2.readSensors())==(2))){
        motor_9.run((9)==M1?-(150):(150));
        motor_10.run((10)==M1?-(0):(0));
    }
    if(((linefollower_2.readSensors())==(3))){
        move(2,100);
    }
    //sonarDetect();
}

/*
 * -----------------------------------
 * Obstacles
 * -----------------------------------
 */
void lookAtSonar()
{
    sonar = ultrasonic_3.distanceCm();
    //emit sonar data but with a reduced frequency
    if( count++ > 50 ){ Serial.println(sonar);  count = 0; }
    if((sonar) < (10)){ //very near
        if(((input)==(119))){
            move(1,0);
            rgbled_7.setColor(0,60,0,0);
            rgbled_7.show();
            //Serial.println("OBSTACLE FROM ARDUINO");
            _delay(0.3);
            move(2,100);
            _delay(1);
            move(2,0);
        }
    }
}
/*
 * -----------------------------------
 * delay
 * -----------------------------------
 */
void _loop(){
}
 
void _delay(float seconds){
    long endTime = millis() + seconds * 1000;
    while(millis() < endTime)_loop();
}

/*
 * -----------------------------------
 * Interpreter
 * -----------------------------------
 */
void remoteCmdExecutor()
{
    if((Serial.available()) > (0  )){
        input = Serial.read();
        //Serial.println(input);
        switch( input ){
          case 119 : move(1,150); break;  //w
          case 115 : move(2,150); break;  //s
          case 97  : move(3,150); break;  //a
          case 100 : move(4,150); break;  //d
          case 104 : move(1,0); stopFollow = true;  break;  //h
          case 102 : move(1,0); stopFollow = false; break;  //f
          default  : move(1,0); stopFollow = true;
        }
    }
}     
/*
 * -----------------------------------
 * Moving
 * -----------------------------------
 */
void move(int direction, int speed)
{
      int leftSpeed = 0;
      int rightSpeed = 0;
      if(direction == 1){ //forward
        	leftSpeed = speed;
        	rightSpeed = speed;
      }else if(direction == 2){ //backward
        	leftSpeed = -speed;
        	rightSpeed = -speed;
      }else if(direction == 3){ //left
        	leftSpeed = -speed;
        	rightSpeed = speed;
      }else if(direction == 4){ //right
        	leftSpeed = speed;
        	rightSpeed = -speed;
      }
      motor_9.run((9)==M1?-(leftSpeed):(leftSpeed));
      motor_10.run((10)==M1?-(rightSpeed):(rightSpeed));
}

/*
 * -----------------------------------
 * setup
 * -----------------------------------
 */
void setup(){
    Serial.begin(115200);
    //Serial.println("start");
}

void loop(){
    rgbled_7.setColor(0,0,60,0);
    rgbled_7.show();
    remoteCmdExecutor();
    lookAtSonar();
    lineFollow();
    _loop();
}