package frc.robot.subsystems.indexer;

import org.littletonrobotics.junction.AutoLog;

public interface IndexerIO {
    @AutoLog
    public static class IndexerIOInputs {
        public double motorVoltage = 0.0;
        public double supplyVoltage = 0.0;
        public double currentAmps = 0.0;
    }

    /** Updates the set of loggable inputs. */
  public default void updateInputs(IndexerIOInputs inputs) {}

  /** Set voltage command */
  public default void setVoltage(double voltage) {}
}
