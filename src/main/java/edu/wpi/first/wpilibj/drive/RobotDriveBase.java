/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.drive;

import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.MotorSafetyHelper;

/**
 * Common base class for drive platforms.
 */
public abstract class RobotDriveBase implements MotorSafety {
  protected double m_deadband = 0.02;
  protected double m_maxOutput = 1.0;
  protected MotorSafetyHelper m_safetyHelper = new MotorSafetyHelper(this);

  /**
   * The location of a motor on the robot for the purpose of driving.
   */
  public enum MotorType {
    kFrontLeft(0), kFrontRight(1), kRearLeft(2), kRearRight(3), kLeft(0),
    kRight(1), kBack(2);

    @SuppressWarnings("MemberName")
    public final int value;

    MotorType(int value) {
      this.value = value;
    }
  }

  public RobotDriveBase() {
    m_safetyHelper.setSafetyEnabled(true);
  }

  public void setDeadband(double deadband) {
    m_deadband = deadband;
  }

  /**
   * Configure the scaling factor for using RobotDrive with motor controllers in a mode other than
   * PercentVbus.
   *
   * @param maxOutput Multiplied with the output percentage computed by the drive functions.
   */
  public void setMaxOutput(double maxOutput) {
    m_maxOutput = maxOutput;
  }

  @Override
  public void setExpiration(double timeout) {
    m_safetyHelper.setExpiration(timeout);
  }

  @Override
  public double getExpiration() {
    return m_safetyHelper.getExpiration();
  }

  @Override
  public boolean isAlive() {
    return m_safetyHelper.isAlive();
  }

  @Override
  public abstract void stopMotor();

  @Override
  public boolean isSafetyEnabled() {
    return m_safetyHelper.isSafetyEnabled();
  }

  @Override
  public void setSafetyEnabled(boolean enabled) {
    m_safetyHelper.setSafetyEnabled(enabled);
  }

  @Override
  public abstract String getDescription();

  /**
   * Limit motor values to the -1.0 to +1.0 range.
   */
  protected double limit(double value) {
    if (value > 1.0) {
      return 1.0;
    }
    if (value < -1.0) {
      return -1.0;
    }
    return value;
  }

  /**
   * Returns 0.0 if the given value is within the specified range around zero. The remaining range
   * between the deadband and 1.0 is scaled from 0.0 to 1.0.
   *
   * @param value    value to clip
   * @param deadband range around zero
   */
  protected double applyDeadband(double value, double deadband) {
    if (Math.abs(value) > deadband) {
      if (value > 0.0) {
        return (value - deadband) / (1.0 - deadband);
      } else {
        return (value + deadband) / (1.0 - deadband);
      }
    } else {
      return 0.0;
    }
  }

  /**
   * Normalize all wheel speeds if the magnitude of any wheel is greater than 1.0.
   */
  protected void normalize(double[] wheelSpeeds) {
    double maxMagnitude = Math.abs(wheelSpeeds[0]);
    for (int i = 1; i < wheelSpeeds.length; i++) {
      double temp = Math.abs(wheelSpeeds[i]);
      if (maxMagnitude < temp) {
        maxMagnitude = temp;
      }
    }
    if (maxMagnitude > 1.0) {
      for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
      }
    }
  }
}
