package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class Arm {

    // TODO: Add the variables you will need here
    // This is how long you want to run the motor in milliseconds.
    // The longer you run it the farther it will go
    long runTime = 2000;

    // Enumeration for Arm Position
    public enum ArmPosition {UP, DOWN}

    // TODO: Make an array for the different power settings (1,-1)

    // TODO: Add the constructor that takes in the hardware map and store the motor in a variable

    public Action setPosition(Arm.ArmPosition position) {
        return new Action() {
            private boolean initialized = false;
            long startTime;

            // This is how long you want to run the motor
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    // Capture the start time
                    startTime = System.currentTimeMillis();
                    initialized = true;

                    //TODO: Set power to the motor depending on the direction you want to go
                }

                // Get the current time
                long currentTime = System.currentTimeMillis();

                // If the time has run out you are done. If not, keep returning true and checking the time
                if (currentTime - startTime > runTime) {
                    //TODO: Set motor power to 0
                    return false; // Return false to stop the action
                } else {
                    return true; // Return true to continue the action
                }
            }
        };
    }
}