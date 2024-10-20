package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

class Claw {
    private final Servo clawServo;

    public Claw(HardwareMap hardwareMap){
        clawServo = hardwareMap.get(Servo.class, "claw");
    }

    public Action open(){
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet){
                if(!initialized){
                    clawServo.setPosition(0.5);
                    initialized = true;
                }
                double servoPosition = clawServo.getPosition();
                return servoPosition >= .5;
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
    }
}
