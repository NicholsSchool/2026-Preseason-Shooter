package frc.robot.subsystems.redirector;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;


public class RedirectorIOSim implements RedirectorIO {

    private static final DCMotor redirectorMotorModel = DCMotor.getKrakenX60(1);

     private final DCMotorSim redirectorMotor =
      new DCMotorSim(
          LinearSystemId.createDCMotorSystem(redirectorMotorModel, 0.025, 1.0),
          redirectorMotorModel);

    public void updateInputs(RedirectorIOInputs inputs){
        redirectorMotor.update(0.02);
        inputs.appliedVolts = redirectorMotor.getInputVoltage();
        inputs.currentAmps = redirectorMotor.getCurrentDrawAmps();
        inputs.currentAngleRad = redirectorMotor.getAngularPositionRad();
        inputs.currentAngleDeg = redirectorMotor.getAngularPositionRad() * 180 / Math.PI;
    }

    public void setVoltage(double voltage) {
        redirectorMotor.setInputVoltage(voltage);
    }
}