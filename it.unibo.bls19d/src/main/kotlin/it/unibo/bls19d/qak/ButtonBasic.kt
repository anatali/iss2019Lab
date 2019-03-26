package it.unibo.bls19d.qak

import java.awt.*
import java.awt.event.ActionListener

class ButtonBasic(frame: Frame, label: String, listener: ActionListener) : java.awt.Button(label) {
    init {
        this.addActionListener(listener)
        frame.add(BorderLayout.WEST, this)
        frame.validate()
    }
}