/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import com.analog.adis16448.frc.ADIS16448_IMU;


public class Drivetrain
{
    private TalonSRX leftFront;
    private TalonSRX leftBack;
    private TalonSRX leftTop;
    private TalonSRX rightFront;
    private TalonSRX rightBack;
    private TalonSRX rightTop;
    public static ADIS16448_IMU gyro;

    double joyRight;
    double joyLeft;

    Drivetrain(TalonSRX leftFront, TalonSRX leftBack, TalonSRX leftTop, TalonSRX rightFront, TalonSRX rightBack, TalonSRX rightTop)
    {
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.leftTop = leftTop;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.rightTop = rightTop;
        gyro = new ADIS16448_IMU();
    }

    public void init()
    {
        leftFront.follow(leftTop);
        leftBack.follow(leftTop);
        rightFront.follow(rightTop);
        rightBack.follow(rightTop);
        leftTop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        rightTop.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        leftTop.setSensorPhase(true);
        rightTop.setSensorPhase(true);
        leftTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        leftTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        leftTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        leftTop.config_kF(Constants.PIDSlotID, Constants.velocKfDrive, Constants.timeoutMS);
        rightTop.config_kP(Constants.PIDSlotID, Constants.velocKpDrive, Constants.timeoutMS);
        rightTop.config_kI(Constants.PIDSlotID, Constants.velocKiDrive, Constants.timeoutMS);
        rightTop.config_kD(Constants.PIDSlotID, Constants.velocKdDrive, Constants.timeoutMS);
        rightTop.config_kF(Constants.PIDSlotID, Constants.velocKfDrive, Constants.timeoutMS);

        //gyro.calibrate();
        //gyro.reset();    
        
        stopAll();
    }

    public void setSidePower(char side, double power)
    {
        if(side == 'l')
        {
            leftTop.set(ControlMode.PercentOutput, -power);
        }
        else if(side == 'r')
        {
            rightTop.set(ControlMode.PercentOutput, power);
        }
    }

    public double getRPMToU100Ms(double RPM)
    {
        return RPM * .1333 ;//.1333 is composed of (80 u / 60 s / 1000 ms * 100)
    }

    public void setSideVeloc(char side, double RPM)
    {
        if(side == 'l')
        {
            leftTop.set(ControlMode.Velocity, -getRPMToU100Ms(RPM));
        }
        else if(side == 'r')
        {
            rightTop.set(ControlMode.Velocity, getRPMToU100Ms(RPM));
        }
    }

    public void stopAll()
    {
        rightTop.set(ControlMode.PercentOutput, 0);
        leftTop.set(ControlMode.PercentOutput, 0);
    }

    public void controlFallback2Stick(XboxController controller)
    {
        joyRight = controller.getY(Hand.kRight);
        joyLeft = controller.getY(Hand.kLeft);
        if(Math.abs(joyRight) < .2 && Math.abs(joyLeft) < .2)
        {
            stopAll();
        }
        else
        {
            setSidePower('l', joyLeft);
            setSidePower('r', joyRight);
        }
    }

    public void controlStraight2StickVelocity(XboxController controller)
    {
        joyRight = controller.getY(Hand.kRight);
        joyLeft = controller.getY(Hand.kLeft);
        if(Math.abs(joyRight) < .2 && Math.abs(joyLeft) < .2)
        {
            stopAll();
        }
        else
        {
            if(Math.abs(joyLeft - joyRight) < .3)
            {
                setSideVeloc('r', joyLeft * 5330);
                setSideVeloc('l', joyLeft * 5330);
            }
            else
            {
                setSidePower('r', joyRight);
                setSidePower('l', joyLeft);
            }
        }
    }
}