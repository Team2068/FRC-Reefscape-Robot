// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.DefaultDrive;
import frc.robot.utility.AutomatedController;
import frc.robot.utility.IO;
import frc.robot.utility.Util;

public class RobotContainer {
  
  public IO io = new IO();
  public final AutomatedController main;
  public final AutomatedController backup;


  private final SendableChooser<Command> auto_selector;
  private Command current_auto;

  public RobotContainer() {
    main = new AutomatedController(0, io);
    backup = new AutomatedController(1, io);

    auto_selector = AutoBuilder.buildAutoChooser();
    auto_selector.onChange((command) -> {current_auto = command;});

    SmartDashboard.putData("Autos",auto_selector);
    SmartDashboard.putData("Run Test Auto", Util.Do(current_auto::schedule));

    SmartDashboard.putData("Main-Controller Mode", main.selector);
    SmartDashboard.putData("Backup-Controller Mode", main.selector);
    io.chassis.setDefaultCommand(new DefaultDrive(io, main.controller));
    // SmartDashboard.putData("Autonomous", ); // TBD
  }

  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
