package it.unibo.bls.devices.gui.segm7;

public class LedSegmentVerticalRight extends LedSegment{

    public LedSegmentVerticalRight(String name, int width, int height){
        super(name, width, height );
    }
    //@Override
    protected void setLedRep(){
        ledRep.addPoint(x+94,y+23 );
        ledRep.addPoint(x+103,y+18 );
        ledRep.addPoint(x+110,y+23  );
        ledRep.addPoint(x+110,y+81);
        ledRep.addPoint(x+103,y+90);
        ledRep.addPoint(x+94,y+81);
     }

}
