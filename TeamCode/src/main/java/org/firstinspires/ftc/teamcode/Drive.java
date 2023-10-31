package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
//import com.qualcomm.robotcore.hardware.Servo;

//import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        boolean fieldCentric = true;

        // Get the motors
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor arm_rt = hardwareMap.dcMotor.get("arm_rt");
        DcMotor arm_lt = hardwareMap.dcMotor.get("arm_lt");
        // servo = hardwareMap.get(Servo.class, "claw");

        // These may be robot dependant
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        // The IMU will be used to capture the heading for field centric driving
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // Set this to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

        /*
        bk_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ft_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ft_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_lt.setMode(DcMotor.RinMode.STOP_AND_RESET_ENCODER);

        bk_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm_lt.setMode(DcMotor.RinMode.RUN_USING_ENCODER);
        */


        arm_lt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm_rt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Press Start When Ready","");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()){
            double left_stick_x = gamepad1.left_stick_x;
            double left_stick_y = -gamepad1.left_stick_y;
            double triggers = gamepad1.left_trigger - gamepad1.right_trigger;
            boolean dpadDown = gamepad1.dpad_down;
            boolean dpadUp = gamepad1.dpad_up;

            if (dpadUp == true){
                arm_rt.setPower(0.75);
                arm_lt.setPower(0.75);
            } else if (dpadDown == true){
                arm_rt.setPower(-0.60);
                arm_lt.setPower(-0.60);
            } else if (dpadDown == false && dpadUp == false) {
                arm_rt.setPower(0);
                arm_lt.setPower(0);
            }

            if (Math.abs(triggers) < 0.05) triggers = 0;
            double bk_lt_power = -left_stick_x + left_stick_y - triggers;
            double ft_lt_power = left_stick_x + left_stick_y - triggers;
            double ft_rt_power = -left_stick_x + left_stick_y + triggers;
            double bk_rt_power = left_stick_x + left_stick_y + triggers;

            // I have seen two ways to get this information. The latter looks less error prone
            //double botHeading = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the robot's rotation
            if(fieldCentric) {

                // This can be used if the robot become uncalibrated. Point the robot away from you and press start.
                if (gamepad1.start) {
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

            telemetry.addData("Heading (degrees)", " %.1f", botHeading * 180.0 / Math.PI);
            telemetry.addData("Left Stick X", left_stick_x);
            telemetry.addData("Left Stick Y", left_stick_y);
            telemetry.addData("Triggers", triggers);
            telemetry.addData("dpadUp", dpadUp);
            telemetry.addData("dpadDown", dpadDown);
            telemetry.update();
        }
        bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0);
    }
}

