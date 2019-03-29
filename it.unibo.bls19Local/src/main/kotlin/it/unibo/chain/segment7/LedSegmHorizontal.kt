package it.unibo.chain.segment7

open class LedSegmHorizontal(name: String, width: Int, height: Int) : LedSegment(name, width, height) {

    override fun setLedRep() {
        ledRep.addPoint(x + 10,  y + 8)
        ledRep.addPoint(x + 100, y + 8)
        ledRep.addPoint(x + 110, y + 15)
        ledRep.addPoint(x + 100, y + 22)
        ledRep.addPoint(x + 10,  y + 22)
        ledRep.addPoint(x + 2,   y + 15)
    }
}
