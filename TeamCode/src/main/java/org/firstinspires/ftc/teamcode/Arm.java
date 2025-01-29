package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Arm {

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

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    initialized = true;
                    armMotor.setTargetPosition(armPositions[position.ordinal()]);
                }

                int currentPosition = armMotor.getCurrentPosition();
                packet.put("arm Position", currentPosition);
                packet.put("arm Target Position", armMotor.getTargetPosition());
                return !(currentPosition == armPositions[position.ordinal()]);
            }
        };
    }
}