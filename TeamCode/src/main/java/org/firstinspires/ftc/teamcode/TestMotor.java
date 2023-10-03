package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name = "testmotor")
public class TestMotor extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor testMotor = hardwareMap.dcMotor.get("test motor");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.a){
                testMotor.setPower(1.0);
            }
            else {
                testMotor.setPower(0);
            }

            testMotor.setPower(-gamepad1.left_stick_y);

            telemetry.addData("left_stick_y",-gamepad1.left_stick_y);
            telemetry.update();
        }
    }
}
