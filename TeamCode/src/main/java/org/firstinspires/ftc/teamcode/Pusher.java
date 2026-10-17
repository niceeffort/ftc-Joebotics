package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Pusher {
    private final Servo pusherServo;

    public enum PusherPosition {GO, STOP}
    private final double[] pusherPositions = new double[]{1.0, 0.0};

    public Pusher(HardwareMap hardwareMap){
        pusherServo = hardwareMap.get(Servo.class, "pusher");
    }

    public Action setPosition(PusherPosition position){
        return new Action() {
            private boolean initialized = false;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    pusherServo.setPosition(pusherPositions[position.ordinal()]);
                    initialized = true;
                }
                double servoPosition = pusherServo.getPosition();
                return servoPosition != pusherPositions[position.ordinal()];
            }
        };
    }
}
