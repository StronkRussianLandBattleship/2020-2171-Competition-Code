/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Compressor;

import edu.wpi.first.wpilibj.XboxController;

import io.github.pseudoresonance.pixy2api.Pixy2;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot 
{
  private Drivetrain drivetrain;
  private Intake intake;  
  private Flywheel flywheel;
  private Index index;

  private Compressor compressor;

  private XboxController driveController;

  Pixy2 camera;
  //private XboxController nonDriveController;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */

  @Override
  public void robotInit() 
  {
    camera = new Pixy2(SPI);
    drivetrain = new Drivetrain(new TalonSRX(Constants.frontLeftMotor), new TalonSRX(Constants.backLeftMotor), new TalonSRX(Constants.topLeftMotor), new TalonSRX(Constants.frontRightMotor), new TalonSRX(Constants.backRightMotor), new TalonSRX(Constants.topRightMotor));
    intake = new Intake(new Talon(Constants.intake));
    flywheel = new Flywheel(new TalonFX(Constants.flywheelMaster), new TalonFX(Constants.flywheelSlave));
    index = new Index(new Talon(Constants.index));
    compressor = new Compressor();

    driveController = new XboxController(0);
    //nonDriveController = new XboxController(1);

    drivetrain.init();
    intake.init();
    flywheel.init();
    index.init();

    compressor.stop();
  }
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() 
  {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() 
  {

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() 
  {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
    drivetrain.controlStraight2StickVelocity(driveController);
    /*lift.controlLift(nonDriveController);
    flywheel.controlFlywheel(nonDriveController);
    index.controlIndex(nonDriveController);
    intake.controlIntake(nonDriveController);*/
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() 
  {
    
  }
}
