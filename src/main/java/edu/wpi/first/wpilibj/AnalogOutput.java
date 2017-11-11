/*----------------------------------------------------------------------------*/
/* Copyright (c) 2014-2017 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.hal.AnalogJNI;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;

/**
 * Analog output class.
 */
public class AnalogOutput extends SensorBase implements LiveWindowSendable {
  private int m_port;
  private int m_channel;

  /**
   * Construct an analog output on a specified MXP channel.
   *
   * @param channel The channel number to represent.
   */
  public AnalogOutput(final int channel) {
    checkAnalogOutputChannel(channel);
    m_channel = channel;

    final int portHandle = AnalogJNI.getPort((byte) channel);
    m_port = AnalogJNI.initializeAnalogOutputPort(portHandle);

    LiveWindow.addSensor("AnalogOutput", channel, this);
    HAL.report(tResourceType.kResourceType_AnalogOutput, channel);
  }

  /**
   * Channel destructor.
   */
  public void free() {
    AnalogJNI.freeAnalogOutputPort(m_port);
    m_port = 0;
    m_channel = 0;
  }

  /**
   * Get the channel of this AnalogOutput.
   */
  public int getChannel() {
    return m_channel;
  }

  public void setVoltage(double voltage) {
    AnalogJNI.setAnalogOutput(m_port, voltage);
  }

  public double getVoltage() {
    return AnalogJNI.getAnalogOutput(m_port);
  }

  /*
   * Live Window code, only does anything if live window is activated.
   */
  @Override
  public String getSmartDashboardType() {
    return "Analog Output";
  }

  private NetworkTableEntry m_valueEntry;

  @Override
  public void initTable(NetworkTable subtable) {
    if (subtable != null) {
      m_valueEntry = subtable.getEntry("Value");
      updateTable();
    } else {
      m_valueEntry = null;
    }
  }

  @Override
  public void updateTable() {
    if (m_valueEntry != null) {
      m_valueEntry.setDouble(getVoltage());
    }
  }

  /**
   * Analog Channels don't have to do anything special when entering the LiveWindow. {@inheritDoc}
   */
  @Override
  public void startLiveWindowMode() {
  }

  /**
   * Analog Channels don't have to do anything special when exiting the LiveWindow. {@inheritDoc}
   */
  @Override
  public void stopLiveWindowMode() {
  }
}
