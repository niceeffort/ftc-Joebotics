package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

class Riser {
    private final DcMotorEx riserMotor;
    private final OverflowEncoder riserEncoder;

    public enum RiserPosition {UP, HIGH_BAR, DOWN}

    private final int[] riserPositions = new int[]{500, 1900, 0};

    public Riser(HardwareMap hardwareMap) {
        riserMotor = hardwareMap.get(DcMotorEx.class, "riser");
        riserEncoder = new OverflowEncoder(new RawEncoder(riserMotor));
        riserEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
        riserMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        riserMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        riserMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        riserMotor.setTargetPosition(riserPositions[RiserPosition.DOWN.ordinal()]);
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

                return motorPosition != riserPositions[position.ordinal()];
            }
        };
    }
}