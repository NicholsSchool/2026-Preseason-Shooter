package frc.robot.subsystems.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;



import frc.robot.Constants;
import frc.robot.Constants.CAN;

public class ShooterIOReal implements ShooterIO {

    private TalonFX shooterMotor;

    public ShooterIOReal(){
        shooterMotor = new TalonFX(CAN.SHOOTER);


         var config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = Constants.ShooterConstants.SHOOTER_MOTOR_CURRENT_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        shooterMotor.getConfigurator().apply(config);

    }

    public void updateInputs(ShooterIOInputs inputs){
        inputs.velocityRPMs = shooterMotor.getVelocity().getValueAsDouble() * 60.0;
        inputs.appliedVolts = shooterMotor.getSupplyVoltage().getValueAsDouble();
        inputs.currentAmps = shooterMotor.getSupplyCurrent().getValueAsDouble();
    }

    @Override
    public void setVoltage(double voltage){
        shooterMotor.setVoltage(voltage);
    }

    @Override
    public void stop(){
        shooterMotor.setVoltage(0.0);
    }
}