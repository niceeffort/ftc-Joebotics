package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {

    // TODO: Add the variables you will need here
    // This is how long you want to run the motor in milliseconds.
    // The longer you run it the farther it will go
    long runTime = 2500;
    private final DcMotorEx armMotor;

    // Enumeration for Arm Position
    public enum ArmPosition {UP, DOWN}
    private final int[] armPositions = new int[] {0,1528};

    public Arm(HardwareMap hardwareMap){

        armMotor = hardwareMap.get(DcMotorEx.class, "arm");
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setTargetPosition(armPositions[ArmPosition.UP.ordinal()]);
        armMotor.setPower(1.0);
    }

    public Action setPosition(Arm.ArmPosition position) {
        return new Action() {
            private boolean initialized = false;
            //long startTime;

            // This is how long you want to run the motor
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    // Capture the start time
                    //startTime = System.currentTimeMillis();
                    initialized = true;

                    //TODO: Set power to the motor depending on the direction you want to go
                    //armMotor.setPower(armPositions[position.ordinal()]);
                    armMotor.setTargetPosition(armPositions[position.ordinal()]);
                }

                int currentPosition = armMotor.getCurrentPosition();
                packet.put("arm Position", currentPosition);
                packet.put("arm Target Position", armMotor.getTargetPosition());
                return !(currentPosition == armPositions[position.ordinal()]);


                // Get the current time
                /*long currentTime = System.currentTimeMillis();

                // If the time has run out you are done. If not, keep returning true and checking the time
                if (currentTime - startTime > runTime) {
                    armMotor.setPower(0.0);
                    return false; // Return false to stop the action
                } else {
                    return true; // Return true to continue the action
                }
                 */
            }
        };
    }
}