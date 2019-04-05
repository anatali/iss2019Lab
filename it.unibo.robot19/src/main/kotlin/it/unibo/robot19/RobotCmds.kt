package it.unibo.robot19

sealed class RobotCmds(val cmd: String, val arg : Int=0, val id : String ="robotCmd"){
    override fun toString() : String{ return "$id(${cmd})" }

    class MoveForward   : RobotCmds("moveForward", -1 )    //forever
    class MoveBackward  : RobotCmds("moveBackward", -1 )  //forever
    class MoveStop      : RobotCmds("alarm" )
    class MoveTurnLeft  : RobotCmds("moveTurnLeft", 400 )
    class MoveTurnRight : RobotCmds("moveTurnRight", 400 )
}
