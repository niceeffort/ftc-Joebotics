package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "ServoTest")
public class ServoTest extends LinearOpMode {

    private CRServo con_servo; // declare private servo class

    @Override
    public void runOpMode() throws InterruptedException {

        con_servo = hardwareMap.crservo.get("con_servo"); // imported CRServo class

        Servo wrist = hardwareMap.servo.get("wrist");

        waitForStart();
        while(opModeIsActive()) {
            if (gamepad1.a) {
                con_servo.setPower(1);
            }
            if (gamepad1.b) {
                con_servo.setPower(-1);
            }
            if (gamepad1.atRest()) {
                con_servo.setPower(0);
            }
            telemetry.update();

           if (gamepad1.a) {
               wrist.setPosition(0);
               telemetry.addLine("pressing a");
               telemetry.update();
           }
           if(gamepad1.b) {
               wrist.setPosition(0);
               telemetry.addLine("pressing b");
               telemetry.update();
           }
        }

    }
}
