package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Pitcher {

    private final DcMotor front_pitcher;
    private final DcMotor back_pitcher;

    // Enumeration for Pitcher Position
    public enum PitcherPosition {GO, STOP}
    private final int[] pitcherPositions = new int[] {75, 0};

    public Pitcher(HardwareMap hardwareMap){

        front_pitcher = hardwareMap.get(DcMotor.class, "front_pitcher");
        back_pitcher = hardwareMap.get(DcMotor.class, "back_pitcher");
        front_pitcher.setDirection(DcMotor.Direction.REVERSE);
        back_pitcher.setDirection(DcMotor.Direction.REVERSE);
        back_pitcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_pitcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        front_pitcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_pitcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_pitcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_pitcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_pitcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        back_pitcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        front_pitcher.setTargetPosition(pitcherPositions[PitcherPosition.GO.ordinal()]);
        back_pitcher.setTargetPosition(pitcherPositions[PitcherPosition.GO.ordinal()]);
        front_pitcher.setTargetPosition(pitcherPositions[PitcherPosition.STOP.ordinal()]);
        back_pitcher.setTargetPosition(pitcherPositions[PitcherPosition.STOP.ordinal()]);
        front_pitcher.setPower(1.0);
        back_pitcher.setPower(1.0);
    }

    public Action setPosition(Pitcher.PitcherPosition position) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    initialized = true;
                    front_pitcher.setTargetPosition(pitcherPositions[position.ordinal()]);
                    back_pitcher.setTargetPosition(pitcherPositions[position.ordinal()]);
                }

                int currentPosition = front_pitcher.getCurrentPosition();
                packet.put("pitcher Position", currentPosition);
                packet.put("pitcher Target Position", front_pitcher.getTargetPosition());
                packet.put("pitcher Target Position", back_pitcher.getTargetPosition());
                return !(currentPosition == pitcherPositions[position.ordinal()]);
            }
        };
    }
}