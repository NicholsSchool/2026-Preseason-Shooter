package frc.robot.subsystems.redirector;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.Constants.CAN;

public class RedirectorIOReal implements RedirectorIO {

    private TalonFX redirectorMotor;
    private final CANcoder redirectorEncoder;

    public RedirectorIOReal(){
        redirectorMotor = new TalonFX(CAN.REDIRECTOR, "shooter");
        redirectorEncoder = new CANcoder(CAN.REDIRECTOR_ENCODER, "shooter");
        


         var config = new TalonFXConfiguration();
        config.CurrentLimits.StatorCurrentLimit = Constants.RedirectorConstants.REDIRECTOR_MOTOR_CURRENT_LIMIT;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        redirectorMotor.getConfigurator().apply(config);

    }

    public void updateInputs(RedirectorIOInputs inputs){
        inputs.appliedVolts = redirectorMotor.getSupplyVoltage().getValueAsDouble();
        inputs.currentAmps = redirectorMotor.getSupplyCurrent().getValueAsDouble();
        // going from 16 to 18 tooth gear 
        inputs.currentAngleRad = (30.0 / 40.0) * redirectorEncoder.getAbsolutePosition().getValueAsDouble() * Math.PI / 1.125 + Units.degreesToRadians(30.0);
        inputs.currentAngleDeg = Units.radiansToDegrees((30.0 / 40.0) * redirectorEncoder.getAbsolutePosition().getValueAsDouble() * Math.PI / 1.125 + Units.degreesToRadians(30.0));
    }

    @Override
    public void setVoltage(double voltage){
        //Forward is reverse on the
        redirectorMotor.setVoltage(-voltage);
    }
}