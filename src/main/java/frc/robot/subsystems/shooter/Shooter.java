package frc.robot.subsystems.shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.util.BradyMathLib;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.AutoLogOutput;
import org.littletonrobotics.junction.Logger;

public class Shooter extends SubsystemBase {
    
    private ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private double setpointRPM = 0.0;
    private double voltageCmd = 0.0;
    PIDController controller = new PIDController(ShooterConstants.kP, 0.0, ShooterConstants.kD);

    public Shooter (ShooterIO io){
        this.io = io;
    }
    
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("shooter", inputs);

        if(setpointRPM == 0.0){
            voltageCmd = 0.0;
        }else{
            voltageCmd += controller.calculate(inputs.velocityRPM, setpointRPM);
        }

        io.setVoltage(voltageCmd);

    }
    
    public void setRPM(double setpoint){
        setpointRPM = setpoint;
        controller.reset();
    }

    @AutoLogOutput
    public double getSetpointRPM(){
        return setpointRPM;
    }

    @AutoLogOutput
    public double getVoltageCmd(){
        return voltageCmd;
    }

    @AutoLogOutput
    public double getRPM(){
      return inputs.velocityRPM;
    }

    public void stop() {
        setRPM(0.0);
    }
}