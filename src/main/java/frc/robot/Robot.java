// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.Queue;

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

  //Creates a motor_timer variable

  private int intake_on = -1;


  
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
    m_frontLeftMotorConfig.openLoopRampRate(.25);
    m_frontRightMotorConfig.openLoopRampRate(.25);
    m_backLeftMotorConfig.follow(m_frontLeftMotor);
    m_backRightMotorConfig.follow(m_frontRightMotor);
    m_frontLeftMotor.configure(m_frontLeftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_backLeftMotor.configure(m_backLeftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_frontRightMotor.configure(m_frontRightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_backRightMotor.configure(m_backRightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_intakeMotor.configure(m_intakeMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // Followers (Replacment for motor controller groups)
    


    //inverts Right side of robot
    m_frontRightMotorConfig.inverted(true);
   

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
  
  
   // Program that is ran when the robot is in "Autonomous Mode"
  
  //Methods for movements
  public void turnLeft() {
    m_robotDrive.tankDrive(.5, .5);
  }

  public void turnRight() {
    m_robotDrive.tankDrive(-.5, -.5);
  }

  public void backUp() {
    m_robotDrive.tankDrive(-.5,-.5);
  }

  public void goForward() { 
    m_robotDrive.tankDrive(-.5, .5);
  }

  public void three_sixty_spin() {
    m_robotDrive.tankDrive(-.5,.5);
  }

  public void stayPut() {
    m_robotDrive.tankDrive(0,0);
  }

  //Method for intake motor
  public void depositCoral() {
    intake_on *= -1;
  }

  //Autonomous methods
  public void auto_A() {
    //Move to reef
    if (m_Timer.get() < 5.0) {
      goForward();
    }
    else if (m_Timer.get() < 5.2) {
      turnLeft();
    }
    else if (m_Timer.get() < 5.5) {
      goForward();
    }
    //Deposit coral
    else if (m_Timer.get() < 6.0) {
      m_intakeMotor.set(-.25);
    }
    //Stay put
    else if (m_Timer.get() < 6.5) {
      m_intakeMotor.set(0);
    }
    //Move to driver station
    else if (m_Timer.get() < 7.0) {
      backUp();
    }
    else if (m_Timer.get() < 7.3) {
      turnRight();
    }
    else if (m_Timer.get() < 11) {
      goForward();
    }
    else if (m_Timer.get() < 11.3) {
      turnRight();
    }
    else if (m_Timer.get() < 12) {
      goForward();
    }
    else if (m_Timer.get() < 13) {
      three_sixty_spin();
    }
    else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_Timer.stop();
    }
  }
  //Method for 
  public void auto_B() {
    //Move forward
    if (m_Timer.get() < 5.0) {
      goForward();
    } 
    //Deposit coral
    //intake motor on
    else if (m_Timer.get() == 5.0) {
      depositCoral();
    }
    //stay put
    else if (m_Timer.get() < 6.0) {
      depositCoral();
    }
    //Move to driver station
    else if (m_Timer.get() < 7.0) {
      backUp();
    }
    else if (m_Timer.get() < 7.5) {
      turnRight();
    }
    else if (m_Timer.get() < 8) {
      turnLeft();
    }
    else if (m_Timer.get() < 12) {
      goForward();
    }
    else if (m_Timer.get() < 12.3) {
      turnRight();
    }
    else if (m_Timer.get() < 13) {
      goForward();
    }
    else if (m_Timer.get() < 13.5) {
      three_sixty_spin();
    }
    else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_Timer.stop();
    }
  }
  public void auto_C() {
    //Set intake motor speed
    if (m_Timer.get() < 5.0) {
      goForward();
    }
    //Deposit coral
    else if (m_Timer.get() < 6.0) {
      depositCoral();
    }
    //Move to driver station
    else if (m_Timer.get() < 7.0) {
      backUp();
    }
    else if (m_Timer.get() < 7.3) {
      turnLeft();
    }
    else if (m_Timer.get() < 11) {
      goForward();
    }
    else if (m_Timer.get() < 11.3) {
      turnLeft();
    }
    else if (m_Timer.get() < 12) {
      goForward();
    }
    else if (m_Timer.get() < 13) {
      three_sixty_spin();
    }
    else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_Timer.stop();
    }
  }
  //Code we use......
  public void auto_D() {
    if (m_Timer.get() < 5.0) {
      goForward();
    }
    //Deposit coral
    
    else if (m_Timer.get() < 6.0) {
      m_intakeMotor.set(-.25);    }
    //Stay put
    else if (m_Timer.get() < 8.0) {
      m_intakeMotor.set(0);
    }
      
    else {
      // stops all autonomous operations
      m_robotDrive.stopMotor();
      m_Timer.stop();
    }
  }
   
  @Override
  public void autonomousPeriodic() {

    
    //choose A, B, or C, or D based upon starting position
    auto_D();
    
  }
  
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
    if (m_Controller.getLeftY() != 0.0 || m_Controller.getRightX() != 0.0){
      m_robotDrive.arcadeDrive(-m_Controller.getRightX(), m_Controller.getLeftY());
    }
    /**
     * B button for top motor warm-up
     * right bumper for firing the note
     * left bumper for intake
     */
    
    //Fires intake motor
    
    if (m_Controller.getBButtonPressed() == true) {
      intake_on *= -1;
    }
    if (intake_on == 1) {
      m_intakeMotor.set(-.2);
    }
    else {
      m_intakeMotor.set(0);
    }
    
    /*
    else if (m_Controller.getBButtonReleased() == true) 
    {
      m_topLaunchMotor.stopMotor();
    }
    */
  }
}
