package org.team9999.game.subsystems

import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.TalonSRX

class Chassis : Subsystems () {

  private val frontRight = TalonSRX(0)
  private val backRight = TalonSRX(1)
  private val frontLeft = TalonSRX(2)
  private val backLeft = TalonSRX(3)

  override fun initDefaultCommands () {
    setDefaultCommand(Command())
  }
}