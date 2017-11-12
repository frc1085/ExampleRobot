package org.team9999.game.subsystems

import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.TalonSRX

class ExampleSubsystem : Subsystem () {

  private val frontRight = TalonSRX(0)

  override fun initDefaultCommand () {
    setDefaultCommand(Command())
  }
}