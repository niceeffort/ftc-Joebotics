package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "BioBuzzTeleop")
public class BioBuzzTeleop extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        boolean fieldCentric = true;
        boolean back_pressed = false;

        // Init motors
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor intake = hardwareMap.dcMotor.get("intake");
        DcMotor launcher = hardwareMap.dcMotor.get("launcher");
        CRServo pusher = hardwareMap.crservo.get("pusher");

        // Set reverse and brake behavior
        ft_lt.setDirection(DcMotorSimple.Direction.REVERSE);
        bk_lt.setDirection(DcMotorSimple.Direction.REVERSE);
        bk_rt.setDirection(DcMotor.Direction.REVERSE);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Init the IMU
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

            // Intake
            if (gamepad2.a) {
                intake.setPower(0.8);
            } else if (gamepad2.b) {
                intake.setPower(0.0);
            }

            //Reverse intake
            if (gamepad2.left_bumper) {
                intake.setPower(-.8);
            } else if (gamepad2.right_bumper) {
                intake.setPower(0.0);
            }

            //Launcher
            if (gamepad2.x) {
                launcher.setPower(0.6);
            } else if (gamepad2.y) {
                launcher.setPower(0.0);
            }

            // Pusher
            if (gamepad2.dpad_up) {
                pusher.setPower(1.0);   // Rotate continuously
            } else if (gamepad2.dpad_down) {
                pusher.setPower(0.0);   // Stop rotating
            }

            //Reverse pusher
            if (gamepad2.dpad_left) {
                pusher.setPower(-1.0);
            } else if (gamepad2.dpad_right) {
                pusher.setPower(0.0);
            }

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

                if (gamepad1.start) {
                    imu.resetYaw();
                }

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