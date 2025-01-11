package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "ServoTest")
public class ServoTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Servo wrist = hardwareMap.servo.get("wrist");

        waitForStart();
        while(opModeIsActive()) {
           if (gamepad1.a) {
               wrist.setPosition(0);
               telemetry.addLine("pressing a");
               telemetry.update();
           }
           if(gamepad1.b) {
               wrist.setPosition(0.8333);
               telemetry.addLine("pressing b");
               telemetry.update();
           }
        }

    }
}
