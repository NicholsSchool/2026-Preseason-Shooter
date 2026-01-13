package frc.robot.subsystems.redirector;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.RedirectorConstants;
import frc.robot.util.LoggedTunableNumber;
import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;



public class Redirector extends SubsystemBase {
  private double setpoint;
  private RedirectorIO io;
  private final RedirectorIOInputsAutoLogged inputs = new RedirectorIOInputsAutoLogged();
  public double voltageCommand;

  private static final LoggedTunableNumber kP = new LoggedTunableNumber("Redirector/kP");
  private static final LoggedTunableNumber kD = new LoggedTunableNumber("Redirector/kD");


  private PIDController controller = new PIDController(0.0, 0.0, 0.0);

  public Redirector(RedirectorIO io) {
    System.out.println("[Init] Creating Redirector");
    this.io = io;

    kP.initDefault(Constants.RedirectorConstants.kRedirectorP);
    kD.initDefault(Constants.RedirectorConstants.kRedirectorD);
  }


  @Override
  public void periodic() {
    io.updateInputs(inputs);
    Logger.processInputs("redirector", inputs);


    // Update tunable numbers
    if (kP.hasChanged(hashCode()) || kD.hasChanged(hashCode())) {
      controller.setP(kP.get()); //kD, and kP may also be K and D inside redirector constants
      controller.setD(kD.get());
    }


    // Reset when disabled
    if (DriverStation.isDisabled()) {
      controller.reset();
        setpoint = Math.toRadians(20.0);
      }


      voltageCommand = RedirectorConstants.kRedirectorP * (setpoint - inputs.currentAngleRad);
      io.setVoltage(MathUtil.clamp(voltageCommand, -2, 2));
    }

   @AutoLogOutput
  public double getAngleRad(){
    return inputs.currentAngleRad;
  }

  public void setVoltage(double volts){
    io.setVoltage(volts);
  }

  @AutoLogOutput
  public double getVoltagePID(){
    return RedirectorConstants.kRedirectorP * (setpoint - inputs.currentAngleRad);
  }

   @AutoLogOutput
   public void setSetpointRad(double angle){
    controller.reset();
    setpoint = angle;
   }

  @AutoLogOutput
  public double getSetpointAngle() {
    return setpoint;
  }


  @AutoLogOutput
  public double getVoltageCommand() {
    return voltageCommand;
  }
}