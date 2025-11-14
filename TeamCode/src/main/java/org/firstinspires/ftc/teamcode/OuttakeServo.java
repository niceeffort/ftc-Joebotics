package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "OuttakeServo")
public class OuttakeServo extends LinearOpMode {

    private Servo outServo;

    @Override
    public void runOpMode() throws InterruptedException {

        outServo = hardwareMap.servo.get("servo"); // imported servo class

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                outServo.setPosition(1);
            }
            if (gamepad1.right_bumper) {
                outServo.setPosition(0);
            }
            if (gamepad1.atRest()) {
                outServo.setPosition(0);
            }
            telemetry.update();

            if (gamepad1.left_bumper) {
                telemetry.addLine("pressing a");
                telemetry.update();
            }
            if (gamepad1.right_bumper) {
                telemetry.addLine("pressing b");
                telemetry.update();
            }
        }
    }
}
