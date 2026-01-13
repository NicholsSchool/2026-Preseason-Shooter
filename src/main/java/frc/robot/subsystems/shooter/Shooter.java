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

    public Shooter (ShooterIO io){
        this.io = io;
    }
    
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("shooter", inputs);
    }

    public void setVoltage(double volts){
        io.setVoltage(volts);
    }
    
    @AutoLogOutput
    public double getRPM(){
      return inputs.velocityRPM;
    }

    public void stop() {
        io.setVoltage(0.0);
    }
}