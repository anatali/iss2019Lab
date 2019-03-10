package it.unibo.bls.devices.gui.segm7;

public class LedSegmentVerticalLeft extends LedSegment{

    public LedSegmentVerticalLeft(String name, int width, int height){
        super(name, width, height );
    }
     @Override
     protected void setLedRep() {
        ledRep.addPoint(x  , y + 23);
        ledRep.addPoint(x + 7, y + 18);
        ledRep.addPoint(x + 14, y + 23);
        ledRep.addPoint(x + 14, y + 81);
        ledRep.addPoint(x + 7, y + 90);
        ledRep.addPoint(x , y + 81);
    }

}
