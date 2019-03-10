package it.unibo.bls.devices.gui.segm7;

public class LedSegmentHorizontal extends LedSegment{

    public LedSegmentHorizontal(String name, int width, int height ){
        super(name, width, height);
    }

    @Override
    protected void setLedRep(){
        ledRep.addPoint(x+10,y+8);
        ledRep.addPoint(x+100,y+8);
        ledRep.addPoint(x+110,y+15);
        ledRep.addPoint(x+100,y+22);
        ledRep.addPoint(x+10,y+22);
        ledRep.addPoint(x+ 2,y+15);
    }
}
