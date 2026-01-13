package frc.robot.subsystems.indexer;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;


import frc.robot.Constants;
import frc.robot.Constants.CAN;

public class IndexerIOReal implements IndexerIO {

    private TalonFX indexerMotor;

    public IndexerIOReal(){
        indexerMotor = new TalonFX(CAN.INDEXER, "shooter");

         var config = new TalonFXConfiguration();
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;
        indexerMotor.getConfigurator().apply(config);

    }

    public void updateInputs(IndexerIOInputs inputs){
        inputs.motorVoltage = indexerMotor.getMotorVoltage().getValueAsDouble();
        inputs.currentAmps = indexerMotor.getStatorCurrent().getValueAsDouble();
        inputs.supplyVoltage = indexerMotor.getSupplyVoltage().getValueAsDouble();
    }

    @Override
    public void setVoltage(double voltage){
        indexerMotor.setVoltage(voltage);
    }

}
