package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "Outreach")
public class outreach extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor launcher = hardwareMap.dcMotor.get("launcher");
        DcMotor bk_rt = hardwareMap.dcMotor.get("bk_rt");
        DcMotor bk_lt = hardwareMap.dcMotor.get("bk_lt");

        bk_rt.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            bk_lt.setPower(-gamepad1.left_stick_y);
            bk_rt.setPower(-gamepad1.right_stick_y);

            telemetry.addData("left stick y", -gamepad1.left_stick_y);
            telemetry.addData("right stick y", -gamepad1.right_stick_y);
            telemetry.update();
        }

        bk_lt.setPower(0);
        bk_rt.setPower(0);

    }
}
