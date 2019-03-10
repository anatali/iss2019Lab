package it.unibo.bls.devices.gui.segm7;

import it.unibo.bls.utils.Utils;

import javax.swing.*;
import java.awt.*;

public class MainLedSegment {

    public static LedSegment[][] ledMatrix;
    
    public static void show(int n){
        if( n == 0) {
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOn();
            ledMatrix[1][1].turnOff();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOn();
        }else if( n == 1){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOff();
            ledMatrix[0][2].turnOff();
            ledMatrix[1][0].turnOn();
            ledMatrix[1][1].turnOff();
            ledMatrix[1][2].turnOff();
            ledMatrix[2][1].turnOff();
          }else if( n == 2){
            ledMatrix[0][0].turnOff();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][0].turnOn();
            ledMatrix[1][2].turnOff();
            ledMatrix[2][1].turnOn();
        } else if( n == 3){
            ledMatrix[0][0].turnOff();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOff();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOn();
          } else if( n == 4){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOff();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOff();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOff();
           } else if( n == 5){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOff();
            ledMatrix[1][0].turnOff();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOn();

        }else if( n == 6){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOff();
            ledMatrix[1][0].turnOn();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOn();

        }else if( n == 7){
            ledMatrix[0][0].turnOff();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOff();
            ledMatrix[1][1].turnOff();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOff();

        }else if( n == 8){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOn();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOn();

        }else if( n == 9){
            ledMatrix[0][0].turnOn();
            ledMatrix[0][1].turnOn();
            ledMatrix[0][2].turnOn();
            ledMatrix[1][0].turnOff();
            ledMatrix[1][1].turnOn();
            ledMatrix[1][2].turnOn();
            ledMatrix[2][1].turnOff();
        }
    }
    public static void main(String[] arguments) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame("Segm7");
        f.setSize(340, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(3, 3,0 ,0));
        f.getContentPane().setBackground(Color.CYAN);

         ledMatrix = new LedSegment[3][3];

        ledMatrix[0][0] = new LedSegmentVerticalRight("seg00", 110, 180);
        ledMatrix[0][1] = new LedSegmentHorizontal("seg01", 110, 180);
        ledMatrix[0][2] = new LedSegmentVerticalLeft("seg02", 110, 180);

        ledMatrix[1][0] = new LedSegmentVerticalRight("seg10", 110, 180);
        ledMatrix[1][1] = new LedSegmentHorizontal("seg11", 110, 180);
        ledMatrix[1][2] = new LedSegmentVerticalLeft("seg12", 110, 180);

        ledMatrix[2][0] = new LedSegmentVerticalRight("seg20", 110, 180);//EMPTY
        ledMatrix[2][1] = new LedSegmentHorizontal("seg21", 110, 180);
        ledMatrix[2][2] = new LedSegmentVerticalLeft("seg22", 110, 180);//EMPTY

        for( int i=0; i<3; i++ )
            for( int j=0; j<3; j++ )
                f.add(ledMatrix[i][j]);

        f.setVisible(true);
        for( int i=0; i<10; i++ ){
            show( i );
            Utils.delay(1000);
        }

      }

}
