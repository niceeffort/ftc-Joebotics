package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Cam {
    private final Servo camServo;

    public enum CamPosition {GO, STOP}
    private final double[] camPositions = new double[]{1.0, 0.0};

    public Cam(HardwareMap hardwareMap){
        camServo = hardwareMap.get(Servo.class, "cam");
    }

    public Action setPosition(CamPosition position){
        return new Action() {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    camServo.setPosition(camPositions[position.ordinal()]);
                    initialized = true;
                }
                double servoPosition = camServo.getPosition();
                return servoPosition != camPositions[position.ordinal()];
            }
        };
    }
}
