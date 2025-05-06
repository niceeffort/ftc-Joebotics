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

class Lift {
    private final DcMotorEx liftMotor;
    private final OverflowEncoder liftEncoder;

    public enum LiftPosition {TOP, HIGH_BAR, BOTTOM}

    private final int[] liftPositions = new int[]{500, 1900, 0};

    private final int tolerance = 100;

    public Lift(HardwareMap hardwareMap) {
        liftMotor = hardwareMap.get(DcMotorEx.class, "lift");
        liftEncoder = new OverflowEncoder(new RawEncoder(liftMotor));
        liftEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setTargetPosition(liftPositions[LiftPosition.BOTTOM.ordinal()]);
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(0.5);
    }

    public Action setPosition(LiftPosition position) {
        return new Action() {
            private boolean initialized = false;
            private final int minPosition = liftPositions[position.ordinal()] - tolerance;
            private final int maxPosition = liftPositions[position.ordinal()] + tolerance;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    liftMotor.setTargetPosition(liftPositions[position.ordinal()]);
                    initialized = true;
                }

                double motorPosition = liftMotor.getCurrentPosition();
                packet.put("liftPosition", motorPosition);

                return !(motorPosition < maxPosition && motorPosition > minPosition);
            }
        };
    }
}