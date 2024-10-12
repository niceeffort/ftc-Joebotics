package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "ClawTest")
public class ClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Servo claw = hardwareMap.servo.get("claw");

        waitForStart();
        while(opModeIsActive()){

            //Set buttons
            boolean a = gamepad1.a;
            boolean b = gamepad1.b;

            if (a) {
                claw.setPosition(0.5);
            } else if (b) {
                claw.setPosition(-0.5);
            }
        }
    }
}


