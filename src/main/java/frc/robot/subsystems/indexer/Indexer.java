package frc.robot.subsystems.indexer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.BooleanSupplier;

import org.littletonrobotics.junction.Logger;

public class Indexer extends SubsystemBase {
    
    private IndexerIO io;
    private final IndexerIOInputsAutoLogged inputs = new IndexerIOInputsAutoLogged();
    
    public Indexer (IndexerIO io){
        this.io = io;
    }
    
    public void periodic(){
        io.updateInputs(inputs);
        Logger.processInputs("Indexer", inputs);
        if (DriverStation.isDisabled()) {}
    }
    public void indexerAuto() {
        io.setVoltage(-3.0);
    }

    public void indexerTele(){
        io.setVoltage(-11.0);
    }
    
    public void stop() {
        io.setVoltage(0.0);
    }

    public Command commandIndexer() {   
        return new FunctionalCommand(
            () -> System.out.println("Outtaking"),
            () -> indexerAuto(),
            interrupted -> stop(),
            () -> false,
            this).withTimeout(0.8);
    } 
}