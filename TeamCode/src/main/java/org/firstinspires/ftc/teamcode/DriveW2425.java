package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "DriveW2425")
public class DriveW2425 extends LinearOpMode{
    WhiteMecanumDrive myDrive = null;
    @Override
    public void runOpMode() throws InterruptedException {

        myDrive = new WhiteMecanumDrive(this);

        boolean fieldCentric = false;
        double powerFactor = 0.75;
        boolean lowPowerMode = false;
        boolean aButtonPress = false;

        // Get the motors
       /* DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor"); */
        DcMotor lwr_arm_left = hardwareMap.dcMotor.get("lwr_arm_left");
        DcMotor lwr_arm_right = hardwareMap.dcMotor.get("lwr_arm_right");
        DcMotor upr_arm = hardwareMap.dcMotor.get("upr_arm");
        Servo claw = hardwareMap.servo.get("claw");
        Servo wrist = hardwareMap.servo.get("wrist");
        //CRServo claw = hardwareMap.get(CRServo.class, "claw");
        //CRServo wrist = hardwareMap.get(CRServo.class, "wrist");

        // This part may be robot dependant
       /* bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE); */
        lwr_arm_right.setDirection((DcMotorSimple.Direction.REVERSE));
        lwr_arm_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lwr_arm_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upr_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

      /*  // The IMU will be used to capture the heading for field centric driving
        IMU imu = hardwareMap.get(IMU.class, "imu");

        // Set this to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters); */

        waitForStart();
        while (opModeIsActive()) {

            myDrive.Update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger, gamepad1.right_trigger);


            /*double left_stick_x = gamepad1.left_stick_x;
            double left_stick_y = -gamepad1.left_stick_y;
            double triggers = gamepad1.left_trigger - gamepad1.right_trigger; */
            boolean dpad_up = gamepad2.dpad_up;
            boolean dpad_down = gamepad2.dpad_down;
            boolean dpad_left = gamepad2.dpad_left;
            boolean dpad_right = gamepad2.dpad_right;
            boolean buttonY = gamepad2.y;
            boolean buttonA = gamepad2.a;
            boolean buttonX = gamepad2.x;
            boolean buttonB = gamepad2.b;


          /*  // Setting motor power
            if (Math.abs(triggers) < 0.05) triggers = 0;
            double bk_lt_power = -left_stick_x + left_stick_y - triggers;
            double ft_lt_power = left_stick_x + left_stick_y - triggers;
            double ft_rt_power = -left_stick_x + left_stick_y + triggers;
            double bk_rt_power = left_stick_x + left_stick_y + triggers; */

            // upper arm movement
            if (dpad_up) {
                upr_arm.setPower(0.75);
            } else if (dpad_down) {
                upr_arm.setPower(-0.75);
            } else {
                upr_arm.setPower(0);
            }

            // lower arm movement
            if (buttonY) {
                lwr_arm_left.setPower(0.75);
                lwr_arm_right.setPower(0.75);
            } else if (buttonA) {
                lwr_arm_left.setPower(-0.5);
                lwr_arm_right.setPower(-0.5);
            } else {
                lwr_arm_left.setPower(0);
                lwr_arm_right.setPower(0);
            }

            // claw movement
            if (dpad_left) {
                claw.setPosition(0.8);
                //claw.setPower(0.2);
            } else if (dpad_right) {
                claw.setPosition(0.2);
                //claw.setPower(-0.2);
            } else {
               // claw.setPower(0);
            }

            //wrist
            if (buttonB) {
                wrist.setPosition(0.8);
                //wrist.setPower(0.2);
            } else if (buttonX) {
                wrist.setPosition(0.2);
                //wrist.setPower(-0.2);
            } else {
                //wrist.setPower(0);
            }

           /* // I have seen two ways to get this information. The latter looks less error prone
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
            bk_rt_power /= max; */

            if (gamepad1.a && !aButtonPress) {
                aButtonPress = true;
                if (lowPowerMode){
                    powerFactor = 1.0;
                    lowPowerMode = false;
                } else{
                    powerFactor = 0.25;
                    lowPowerMode = true;
                }

            } else if (!gamepad1.a && aButtonPress) {
                aButtonPress = false;
            }
           /* bk_lt.setPower(bk_lt_power * powerFactor);
            ft_lt.setPower(ft_lt_power * powerFactor);
            ft_rt.setPower(ft_rt_power * powerFactor);
            bk_rt.setPower(bk_rt_power * powerFactor); */
        }

        /* bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0); */

    }

}
