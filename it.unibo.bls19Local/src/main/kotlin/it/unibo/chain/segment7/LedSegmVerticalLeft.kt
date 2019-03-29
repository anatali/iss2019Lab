package it.unibo.chain.segment7

open class LedSegmVerticalLeft(name: String, width: Int, height: Int) : LedSegment(name, width, height) {
    override fun setLedRep() {
        ledRep.addPoint(x, y + 23)
        ledRep.addPoint(x + 7, y + 18)
        ledRep.addPoint(x + 14, y + 23)
        ledRep.addPoint(x + 14, y + 81)
        ledRep.addPoint(x + 7, y + 90)
        ledRep.addPoint(x, y + 81)
    }

}
