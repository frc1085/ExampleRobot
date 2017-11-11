package org.team9999.game.subsystems

class climber : subsystems () {

    private val door = TalonSRX(0)
    private val speedcontroler = TalonSRX(1)

    override fun nitDefaultCommand () {
    setDefaultCommand(Command())
  }
}