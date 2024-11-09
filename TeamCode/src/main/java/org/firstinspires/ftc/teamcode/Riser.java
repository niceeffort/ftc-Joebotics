package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Riser {
    private final DcMotor riserMotor;

    public enum RiserPosition {UP, DOWN}

    private final int[] riserPositions = new int[]{500, 0};

    public Riser(HardwareMap hardwareMap) {
        riserMotor = hardwareMap.get(DcMotor.class, "back_left_motor");
        riserMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        riserMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        riserMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        riserMotor.setPower(0.5);
    }

    public Action setPosition(RiserPosition position) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    riserMotor.setTargetPosition(riserPositions[position.ordinal()]);
                    initialized = true;
                }
                double motorPosition = riserMotor.getCurrentPosition();
                return motorPosition == riserPositions[position.ordinal()];
            }
        };
    }
}