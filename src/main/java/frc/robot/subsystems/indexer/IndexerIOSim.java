package frc.robot.subsystems.indexer;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

public class IndexerIOSim implements IndexerIO {

    private static final DCMotor indexerMotorModel = DCMotor.getKrakenX60(1);

     private final DCMotorSim indexerMotor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(indexerMotorModel, 0.025, 2.0),
          indexerMotorModel);

    public void updateInputs(IndexerIOInputs inputs){
        inputs.supplyVoltage = indexerMotor.getInputVoltage();
        inputs.currentAmps = indexerMotor.getCurrentDrawAmps();
    }

    public void setVoltage(double voltage) {
        indexerMotor.setInputVoltage(voltage);
    }
}

