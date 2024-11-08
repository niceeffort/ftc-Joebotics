package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private final Servo clawServo;

    public enum ClawPosition {OPEN, CLOSE}
    private final double[] clawPositions = new double[]{1.0, 0.0};

    public Claw(HardwareMap hardwareMap){
        clawServo = hardwareMap.get(Servo.class, "claw");
    }

    public Action setPosition(ClawPosition position){
        return new Action() {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    clawServo.setPosition(clawPositions[position.ordinal()]);
                    initialized = true;
                }
                double servoPosition = clawServo.getPosition();
                return servoPosition != clawPositions[position.ordinal()];
            }
        };
    }
}
