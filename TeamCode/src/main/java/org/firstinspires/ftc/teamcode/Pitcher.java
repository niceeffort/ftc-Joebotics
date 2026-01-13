package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Pitcher {

    private final DcMotorEx pitcherMotor;
    private final DcMotorEx pitcherMotor2;

    // Enumeration for Pitcher Position
    public enum PitcherPosition {GO, STOP}
    private final int[] pitcherPositions = new int[] {50, 0};

    public Pitcher(HardwareMap hardwareMap){

        pitcherMotor = hardwareMap.get(DcMotorEx.class, "pitcher");
        pitcherMotor2 = hardwareMap.get(DcMotorEx.class, "pitcher2");
        pitcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pitcherMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pitcherMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitcherMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pitcherMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pitcherMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pitcherMotor.setTargetPosition(pitcherPositions[PitcherPosition.GO.ordinal()]);
        pitcherMotor2.setTargetPosition(pitcherPositions[PitcherPosition.GO.ordinal()]);
        pitcherMotor.setTargetPosition(pitcherPositions[PitcherPosition.STOP.ordinal()]);
        pitcherMotor2.setTargetPosition(pitcherPositions[PitcherPosition.STOP.ordinal()]);
        pitcherMotor.setPower(1.0);
        pitcherMotor2.setPower(1.0);
    }

    public Action setPosition(Pitcher.PitcherPosition position) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    initialized = true;
                    pitcherMotor.setTargetPosition(pitcherPositions[position.ordinal()]);
                    pitcherMotor2.setTargetPosition(pitcherPositions[position.ordinal()]);
                }

                int currentPosition = pitcherMotor.getCurrentPosition();
                packet.put("pitcher Position", currentPosition);
                packet.put("pitcher Target Position", pitcherMotor.getTargetPosition());
                packet.put("pitcher Target Position", pitcherMotor2.getTargetPosition());
                return !(currentPosition == pitcherPositions[position.ordinal()]);
            }
        };
    }
}