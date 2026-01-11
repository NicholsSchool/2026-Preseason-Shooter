package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONAVX;
import frc.robot.subsystems.drive.GyroIORedux;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOMaxSwerve;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.ShooterIO;
import frc.robot.subsystems.shooter.ShooterIOReal;
import frc.robot.subsystems.shooter.ShooterIOSim;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public final Drive drive;
  public final Shooter shooter;
    //since it's testing, only one controller
    public static CommandXboxController driveController = new CommandXboxController(0);

  public RobotContainer() {
    switch (Constants.getRobot()) {

      case ROBOT_REAL:
        // Real robot, kraken swerve for comp bot
        drive =
            new Drive(
                new GyroIORedux(),
                new ModuleIOTalonFX(0),
                new ModuleIOTalonFX(1),
                new ModuleIOTalonFX(2),
                new ModuleIOTalonFX(3));
        shooter = new Shooter(new ShooterIOReal());
        break;
        
      case ROBOT_REAL_FRANKENLEW:
        //real robot, maxswerve and 
        drive =
            new Drive(
                new GyroIONAVX(),
                new ModuleIOMaxSwerve(0),
                new ModuleIOMaxSwerve(1),
                new ModuleIOMaxSwerve(2),
                new ModuleIOMaxSwerve(3));
        shooter = new Shooter(new ShooterIOReal());
        break;

      case ROBOT_SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim(),
                new ModuleIOSim());
        shooter = new Shooter(new ShooterIOSim());
        break;
      default:
        // Replayed robot, disable IO implementations since the replay
        // will supply the data.
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        shooter = new Shooter(new ShooterIOSim());
        break;

    }

      configureButtonBindings();

    }
 
  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    drive.setDefaultCommand(
      DriveCommands.joystickDrive(
          drive,
          () -> -driveController.getLeftY() * Constants.DriveConstants.LOW_GEAR_SCALER,
          () -> -driveController.getLeftX() * Constants.DriveConstants.LOW_GEAR_SCALER,
          () -> -driveController.getRightX() * Constants.DriveConstants.TURNING_SCALAR,
          () -> Constants.DRIVE_ROBOT_RELATIVE));
    shooter.setDefaultCommand(new InstantCommand(() -> shooter.stop(), shooter));
  }

  // /**
  //  * @return the command to run in autonomous
  //  */
  public Command getAutonomousCommand() {


     try{
        // Load the path you want to follow using its name in the GUI
        // PathPlannerPath path = PathPlannerPath.fromPathFile("Example Path");
        // System.out.println("ACTIVE PATH CALL BACK 3");
        // return AutoBuilder.followPath(path);
          return new InstantCommand();
    } catch (Exception e) {
        DriverStation.reportError("no autos: " + e.getMessage(), e.getStackTrace());
        return Commands.none();
    }

    //   // var path = PathPlannerAuto.getStaringPoseFromAutoFile("TestAuto");
    //   // var path = PathPlannerPath.fromChoreoTrajectory("New Path");
    //   // NamedCommands.registerCommand("RunIntake", intake.runEatCommand().withTimeout(1.0));
    //   // NamedCommands.registerCommand("StopIntake", new InstantCommand(() -> intake.stop(),
    // intake));
    //   // NamedCommands.registerCommand(
    //   //     "Shoot", new InstantCommand(() -> new Shoot(shooter, intake,
    // indexer)).withTimeout(3));
    //   // drive.setPose(PathPlannerAuto.getStaringPoseFromAutoFile("New Auto"));
    //   // return new PathPlannerAuto("New Auto");
    //   // return AutoBuilder.followPath(PathPlannerPath.fromPathFile("TestPath"));
    //registerNamedCommands();
    //return autoChooser.get();
  }
}
