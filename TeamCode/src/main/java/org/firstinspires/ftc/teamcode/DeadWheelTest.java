package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="DeadWheelTest")
public class DeadWheelTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //DcMotor parallelDrive = hardwareMap.get(DcMotor.class, "parallel");
        DcMotor perpendicularDrive = hardwareMap.get(DcMotor.class, "back_right_motor");

        waitForStart();

        while(opModeIsActive()){
            //telemetry.addData("parallelDrive", parallelDrive.getCurrentPosition());
            telemetry.addData("perpendicularDrive", perpendicularDrive.getCurrentPosition());
            telemetry.update();
        }
    }
}
