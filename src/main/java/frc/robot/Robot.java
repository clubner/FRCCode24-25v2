// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

//import edu.wpi.first.cameraserver.CameraServer;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  //Constructs and initializes spark max objects
  //front left
  private final SparkBase m_frontLeftMotor = new SparkMax(4, MotorType.kBrushed);
  private final SparkBaseConfig m_frontLeftMotorConfig = new SparkMaxConfig();
  //back left
  private final SparkMax m_backLeftMotor = new SparkMax(1, MotorType.kBrushed);
  private final SparkMaxConfig m_backLeftMotorConfig = new SparkMaxConfig();
  //front right
  private final SparkBase m_frontRightMotor = new SparkMax(5, MotorType.kBrushed);
  private final SparkBaseConfig m_frontRightMotorConfig = new SparkMaxConfig();
  //back right
  private final SparkMax m_backRightMotor = new SparkMax(2, MotorType.kBrushed);
  private final SparkMaxConfig m_backRightMotorConfig = new SparkMaxConfig();
  //intake spark max object
  private final SparkMax m_intakeMotor = new SparkMax(3, MotorType.kBrushless);
  private final SparkMaxConfig m_intakeMotorConfig = new SparkMaxConfig();


  //Constructs and initializes a Timer Object
  private final Timer m_Timer = new Timer();
  
  //constructs and initializes a differential drive object
  private final DifferentialDrive m_robotDrive 
    = new DifferentialDrive(m_frontLeftMotor::set, m_frontRightMotor::set);
  
  //constructs an xbox controller object
  private XboxController m_Controller;

  /**
   * Robot class that stores all information that needs to be transfered to the super class
   */
  public Robot() 
  {
    //adds child to sendable Registry (Whatever that means)
    SendableRegistry.addChild(m_robotDrive, m_frontLeftMotor);
    SendableRegistry.addChild(m_robotDrive, m_frontRightMotor);
  }

  /**
   * Initialization class that allows for set up for later code
   */
  @Override
  public void robotInit() {
    //Configurations
    m_frontLeftMotor.configure(m_frontLeftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_backLeftMotor.configure(m_backLeftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_frontRightMotor.configure(m_frontRightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_backRightMotor.configure(m_backRightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Followers (Replacment for motor controller groups)
    m_backLeftMotorConfig.follow(m_frontLeftMotor);
    m_backRightMotorConfig.follow(m_frontRightMotor);


    //inverts Right side of robot
    m_frontRightMotorConfig.inverted(true);
    m_backRightMotorConfig.inverted(true);

    //initializes Controller Object
    m_Controller = new XboxController(0);

  

    // starts live view from robot webcam
    //CameraServer.startAutomaticCapture();'''
  }

  /*/
   * Autonomous initialization class that creates objects or resets certain variables
   * for autonomous Periodic
   */
   
  @Override
  public void autonomousInit() 
  {
    // resets timer for autonomous
    m_Timer.restart();
  }
  
  /*
   * Program that is ran when the robot is in "Autonomous Mode"
   */
  /* 
  @Override
  public void autonomousPeriodic() {
    //Field Position A
    //Set intake motor speed
    if (m_Timer.get() < 2.0) {
      m_intakeMotor.set(1.0);
    } 
    //Move to reef
    else if (m_Timer.get() >= 2.0 && m_Timer.get() < 5.0) {
      m_robotDrive.tankDrive(0.5, 0.5);
      m_robotDrive.
    }
    else if (m_Timer.get() >= 2.0 && m_Timer.get() < 5.0) {

    }
    //Deposit coral
    //Move to driver station
    {

    }

    } else if (m_Timer.get() > 5.0 && m_Timer.get() < 9.0) 
    {
      //both motors reverse
      
      m_robotDrive.tankDrive(0.5, 0.5);
    } 
    
    else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_Timer.stop();
    }
  }
  */
  /**
   * Program that is ran when robot is in "Teleoperated Mode"
   */
  
  @Override
  public void teleopPeriodic() 
  {
    /*
    Drive with arcade drive.
    That means that the Y axis drives forward
    and backward, and the X turns left and right.
    */
    if (m_Controller.getLeftY() != 0.0 || m_Controller.getRightX() != 0.0)
    {
      m_robotDrive.arcadeDrive(m_Controller.getLeftY(), m_Controller.getRightX());
    }
    /**
     * B button for top motor warm-up
     * right bumper for firing the note
     * left bumper for intake
     */
    
    //Prepares top motor for firing
    /* 
    if (m_Controller.getBButtonPressed() == true)
    {
      m_topLaunchMotor.set(1.0);
    } 
    */
    /*
    else if (m_Controller.getBButtonReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
    }
    */
    /* 
    //activate bottom motor for active firing
    if (m_Controller.getRightBumperPressed() == true)
    {
      m_bottomLaunchMotor.set(1.0);
    } else if (m_Controller.getRightBumperReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
      m_bottomLaunchMotor.stopMotor();
    }

    //if statement for Reciever
    if (m_Controller.getLeftBumperPressed() == true)
    {
      m_topLaunchMotor.set(-0.5);
      m_bottomLaunchMotor.set(-0.2);
    } else if (m_Controller.getLeftBumperReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
      m_bottomLaunchMotor.stopMotor();
    }
    */
  }
}
