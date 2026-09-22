package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "DriveBotTeleop")
public class DriveBotTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        boolean fieldCentric = true;
        boolean back_pressed = false;

        DcMotor bk_rt = hardwareMap.dcMotor.get("bk_rt");
        DcMotor bk_lt = hardwareMap.dcMotor.get("bk_lt");
        DcMotor ft_rt = hardwareMap.dcMotor.get("ft_rt");
        DcMotor ft_lt = hardwareMap.dcMotor.get("ft_lt");

        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();

        waitForStart();

        while (opModeIsActive()) {

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;

            double rotation = gamepad1.left_trigger - gamepad1.right_trigger;

            if (Math.abs(rotation) < 0.05) {
                rotation = 0; }
            if (gamepad1.back && !back_pressed) {
                fieldCentric = !fieldCentric;
                back_pressed = true; }
            if (!gamepad1.back) {
                back_pressed = false; }
            if (gamepad1.start) {
                imu.resetYaw(); }

            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            if (fieldCentric) {
                double newX = x * Math.cos(botHeading) + y * Math.sin(botHeading);
                double newY = -x * Math.sin(botHeading) + y * Math.cos(botHeading);
                x = newX;
                y = newY;
            }

            double bk_lt_power = -x + y - rotation;
            double ft_lt_power = x + y - rotation;
            double ft_rt_power = -x + y + rotation;
            double bk_rt_power = x + y + rotation;

            double max = Math.max(1.0, Math.abs(bk_lt_power));

            max = Math.max(max, Math.abs(ft_lt_power));
            max = Math.max(max, Math.abs(ft_rt_power));
            max = Math.max(max, Math.abs(bk_rt_power));

            bk_lt_power /= max;
            ft_lt_power /= max;
            ft_rt_power /= max;
            bk_rt_power /= max;

            bk_lt.setPower(bk_lt_power);
            ft_lt.setPower(ft_lt_power);
            ft_rt.setPower(ft_rt_power);
            bk_rt.setPower(bk_rt_power);

            telemetry.addData("Field Centric", fieldCentric);
            telemetry.addData("Heading", Math.toDegrees(botHeading));
            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.update();
        }

        bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0);
    }
}