package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Main Robot")
public class MainRobot extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        boolean fieldCentric = false;
        double MAX_ARM_POWER = .5;
        boolean back_pressed = false;


        // Get the motors
        DcMotor bk_rt = hardwareMap.dcMotor.get("bk_rt");
        DcMotor bk_lt = hardwareMap.dcMotor.get("bk_lt");
        DcMotor ft_rt = hardwareMap.dcMotor.get("ft_rt");
        DcMotor ft_lt = hardwareMap.dcMotor.get("ft_lt");
        DcMotor arm = hardwareMap.dcMotor.get("arm");
        DcMotor winch = hardwareMap.dcMotor.get("winch");
        Servo claw = hardwareMap.servo.get("claw");
        Servo small_lift = hardwareMap.servo.get("small_lift");
        Servo launcher = hardwareMap.servo.get("launcher");

        // Set initial position
        launcher.setPosition(0);

        // Setting the brake behavior for winch and arm
        winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // These may be robot dependant
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        // The IMU will be used to capture the heading for field centric driving
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // Set this to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        // Resetting the YAW for now
        imu.resetYaw();

        /*
        bk_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ft_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ft_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bk_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        */

        telemetry.addData("Press Start When Ready", "");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            double left_stick_x = gamepad1.left_stick_x;
            double left_stick_y = -gamepad1.left_stick_y;
            double triggers = gamepad1.left_trigger - gamepad1.right_trigger;

            if (Math.abs(triggers) < 0.05) triggers = 0;
            double bk_lt_power = -left_stick_x + left_stick_y - triggers;
            double ft_lt_power = left_stick_x + left_stick_y - triggers;
            double ft_rt_power = -left_stick_x + left_stick_y + triggers;
            double bk_rt_power = left_stick_x + left_stick_y + triggers;

            // I have seen two ways to get this information. The latter looks less error prone
            //double botHeading = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            if(gamepad1.back && !back_pressed){
                fieldCentric = !fieldCentric;
                back_pressed = true;
            }
            else if(!gamepad1.back){
                back_pressed = false;
            }

            // Rotate the movement direction counter to the bot's rotation
            if (fieldCentric) {

                // This can be used if the robot become uncalibrated. Point the robot away from you and press start.
                if (gamepad1.start) {
                    imu.initialize(parameters);
                    imu.resetYaw();
                }

                double rotX = left_stick_x * Math.cos(-botHeading) - left_stick_y * Math.sin(-botHeading);
                double rotY = left_stick_x * Math.sin(-botHeading) + left_stick_y * Math.cos(-botHeading);
                bk_lt_power = -rotX + rotY - triggers;
                ft_lt_power = rotX + rotY - triggers;
                ft_rt_power = -rotX + rotY + triggers;
                bk_rt_power = rotX + rotY + triggers;
            }

            // Normalize the power
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

            // Arm control
            double arm_triggers = gamepad2.left_trigger - gamepad2.right_trigger;
            arm.setPower(arm_triggers * MAX_ARM_POWER);

            // Claw control
            if (gamepad2.a) { //open
                claw.setPosition(0);
            } else if (gamepad2.y) { //close
                claw.setPosition(.25);
            }

            //added
            if (gamepad1.a) { //open
                small_lift.setPosition(.75);
            } else if (gamepad1.y) { //close
                small_lift.setPosition(.3);
            }

            //Launcher Control
            if (gamepad2.b) {
                if (gamepad2.dpad_right) {
                    launcher.setPosition(.5);
                } else if (gamepad2.dpad_left) {
                    launcher.setPosition(0);
                }
            }

            // Winch control
            if (gamepad2.b) {
                if (gamepad2.dpad_up) {
                    winch.setPower(1.0);
                } else if (gamepad2.dpad_down) {
                    winch.setPower(-1.0);
                } else {
                    winch.setPower(0.0);
                }
            } else {
                winch.setPower(0.0);
            }

            telemetry.addData("Field Centric", fieldCentric);
            telemetry.addData("Heading (degrees)", " %.1f", botHeading * 180.0 / Math.PI);
            telemetry.addData("Left Stick X", left_stick_x);
            telemetry.addData("Left Stick Y", left_stick_y);
            telemetry.addData("Triggers", triggers);
            telemetry.update();
        }
        bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0);
    }
}
