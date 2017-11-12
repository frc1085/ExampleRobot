package org.team9999.game

import edu.wpi.first.wpilibj.IterativeRobot
import edu.wpi.first.wpilibj.command.Scheduler

class Robot : IterativeRobot () {
  override fun robotInit () {}
  override fun autonomousInit () {}
  override fun teleopInit () {}

  override fun autonomousPeriodic () = Scheduler.getInstance().run()
  override fun teleopPeriodic () = Scheduler.getInstance().run()
}