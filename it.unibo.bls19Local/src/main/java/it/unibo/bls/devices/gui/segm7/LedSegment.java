package it.unibo.bls.devices.gui.segm7;

import it.unibo.bls.utils.Utils;
import javax.swing.*;
import java.awt.*;

public class LedSegment extends JPanel {
    private final static int SEGMENT_NUMBER = 7;
    private final static int A = 0;
    private final static int B = 1;
    private final static int C = 2;
    private final static int D = 3;
    private final static int E = 4;
    private final static int F = 5;
    private final static int G = 6;

    private final static int OFF     = 0;
    private final static int ON      = 1;
    private final static int zero[]  = { ON, ON, ON, ON, ON, ON, OFF };
    private final static int one[]   = { OFF, ON, ON, OFF, OFF, OFF, OFF };
    private final static int two[]   = { ON, ON, OFF, ON, ON, OFF, ON };
    private final static int three[] = { ON, ON, ON, ON, OFF, OFF, ON };
    private final static int four[]  = { OFF, ON, ON, OFF, OFF, ON, ON };
    private final static int five[]  = { ON, OFF, ON, ON, OFF, ON, ON };
    private final static int six[]   = { ON, OFF, ON, ON, ON, ON, ON };
    private final static int seven[] = { ON, ON, ON, OFF, OFF, OFF, OFF };
    private final static int eight[] = { ON, ON, ON, ON, ON, ON, ON };
    private final static int nine[]  = { ON, ON, ON, ON, OFF, ON, ON };

    private final static Color off = Color.green.darker().darker().darker().darker();
    private final static Color on  = Color.green.brighter();

    protected String name;
    protected int x = 0;
    protected int y = 0;
    protected Polygon ledRep = new Polygon();
    protected boolean ledState = false;

/*
Constructor
 */
     public LedSegment( String name, int width, int height ) {
         this.name = name;
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
        setOpaque(true);
        setBackground(Color.black);
        setLedRep();
        System.out.println("LedSegment " + name + " | CREATED " + width +":"+ height);
     }

     protected void setLedRepOld(){
         ledRep.addPoint(x+20,y+8);
         ledRep.addPoint(x+90,y+8);
         ledRep.addPoint(x+98,y+15);
         ledRep.addPoint(x+90,y+22);
         ledRep.addPoint(x+20,y+22);
         ledRep.addPoint(x+12,y+15);
     }
     protected void setLedRep(){
         System.out.println("LedSegment " + name + " | setLedRep");
        ledRep.addPoint(x+91,y+23);
        ledRep.addPoint(x+98,y+18);
        ledRep.addPoint(x+105,y+23);
        ledRep.addPoint(x+105,y+81);
        ledRep.addPoint(x+98,y+89);
        ledRep.addPoint(x+91,y+81);
     }

    @ Override
    public void paintComponent(Graphics g) {
        //System.out.println("LedSegment | paint");
        super.paintComponent(g); // this is needed to set the background color
        setSegmentState( g );
    }

    public void turnOn(){
        //setSegmentState(this.getGraphics(), treu);
        System.out.println("LedSegment " + name +" | turnOn");
        ledState = true;
        repaint();
     }
    public void turnOff(){
        //setSegmentState( this.getGraphics(), OFF );
        System.out.println("LedSegment " + name + " | turnOff");
        ledState = false;
        repaint();
    }
    protected void setSegmentState( Graphics g ) {
        if ( ledState  )  g.setColor(on);
        else g.setColor(off);
        g.fillPolygon(ledRep);
        g.drawPolygon(ledRep);
        //repaint();
    }


/*
For rapid check
 */
    public static void main(String[] arguments) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame f = new JFrame("Segm7");
        f.setSize(240, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(1, 2));

        f.getContentPane().setBackground(Color.BLUE);
        LedSegment secs0 = new LedSegment("secs0", 110, 180);
        LedSegment secs1 = new LedSegment("secs1", 110, 180);
        f.add(secs0);
        f.add(secs1);
        f.setVisible(true);

        secs0.turnOn();
        Utils.delay(1000);
        secs0.turnOff();
        secs1.turnOn();
    }
}
