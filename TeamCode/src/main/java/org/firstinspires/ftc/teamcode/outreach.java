package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

@TeleOp(name = "Outreach")
public class outreach extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor launcher = hardwareMap.dcMotor.get("launcher");
        DcMotor bk_rt = hardwareMap.dcMotor.get("bk_rt");
        DcMotor bk_lt = hardwareMap.dcMotor.get("bk_lt");
        Servo claw = hardwareMap.servo.get("claw");
        IMU imu = hardwareMap.get(IMU.class,"imu");

        RevHubOrientationOnRobot.LogoFacingDirection logoDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
        RevHubOrientationOnRobot.UsbFacingDirection  usbDirection  = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
        RevHubOrientationOnRobot orientationOnRobot = new RevHubOrientationOnRobot(logoDirection, usbDirection);

        imu.initialize(new IMU.Parameters(orientationOnRobot));

        bk_rt.setDirection(DcMotorSimple.Direction.REVERSE);
        launcher.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){

            //bk_lt.setPower(-gamepad1.left_stick_y);
            //bk_rt.setPower(-gamepad1.right_stick_y);

            double leftstick_y = gamepad1.left_stick_y;
            bk_lt.setPower(leftstick_y * .5);
            bk_rt.setPower(leftstick_y * .5);

            if(gamepad1.a){
                launcher.setPower(1.0);
            } else{
                launcher.setPower(0);
            }


            if(gamepad1.left_bumper){
                claw.setPosition(0);
            } else if (gamepad1.right_bumper) {
                claw.setPosition(1);
            }

            float robotHeading = imu.getRobotOrientation(AxesReference.INTRINSIC,AxesOrder.ZYX,AngleUnit.DEGREES).firstAngle;

            telemetry.addData("left stick y", -gamepad1.left_stick_y);
            telemetry.addData("right stick y", -gamepad1.right_stick_y);
            telemetry.addLine()
                .addData("robot heading", "%.1f", robotHeading);
                //.addData()
            telemetry.update();
        }

        bk_lt.setPower(0);
        bk_rt.setPower(0);

    }
}
