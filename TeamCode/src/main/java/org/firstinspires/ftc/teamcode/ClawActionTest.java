package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {
    private final Servo clawServo;

    public enum ClawPosition {OPEN, CLOSE}
    private final double[] clawPositions = new double[]{1.0, 0.0};

    public Claw(HardwareMap hardwareMap){
        clawServo = hardwareMap.get(Servo.class, "claw");
    }

    public Action open() {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    clawServo.setPosition(0.5);
                    initialized = true;
                }
                double servoPosition = clawServo.getPosition();
                return servoPosition >= .5;
            }
        };
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

@Autonomous(name = "ClawActionTest")
public class ClawActionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException{
        Claw clawAction = new Claw(hardwareMap);
        waitForStart();
        Actions.runBlocking(clawAction.open());
        Actions.runBlocking(new SequentialAction(
                                clawAction.setPosition(Claw.ClawPosition.OPEN),
                                clawAction.setPosition(Claw.ClawPosition.CLOSE)));
    }
}
