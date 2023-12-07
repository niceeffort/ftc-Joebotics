package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

//import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Drive")
public class Drive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        boolean fieldCentric = false;
        double powerFactor = 1.0;
        boolean lowPowerMode = false;
        boolean aButtonPress = false;
        boolean button_2bPressed = false;
        int lift_position = 0;
        // Get the motors
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor arm_rt = hardwareMap.dcMotor.get("arm_rt");
        DcMotor arm_lt = hardwareMap.dcMotor.get("arm_lt");
        DcMotor rotator = hardwareMap.dcMotor.get("rotator");
        Servo claw = hardwareMap.get(Servo.class, "claw");
        Servo arm_lift = hardwareMap.get(Servo.class, "arm_lift");

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



        bk_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        ft_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        */

        arm_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        arm_rt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm_lt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);



        arm_lt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm_rt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        telemetry.addData("Press Start When Ready","");
        telemetry.update();

        waitForStart();
        while (opModeIsActive()){
            double left_stick_x = gamepad1.left_stick_x;
            double left_stick_y = -gamepad1.left_stick_y;
            double triggers = gamepad1.left_trigger - gamepad1.right_trigger;
            boolean dpadDown = gamepad2.dpad_down;
            boolean dpadUp = gamepad2.dpad_up;
            boolean bumper_left = gamepad2.left_bumper;
            boolean bumper_right = gamepad2.right_bumper;
            boolean button_1b = gamepad1.b;
            boolean button_1x = gamepad1.x;
            boolean button_2a = gamepad2.a;
            boolean button_2y = gamepad2.y;
            boolean button_2b = gamepad2.b;





           //open and close claw - working

           if (button_2a == true && button_2y == false) {
               claw.setDirection(Servo.Direction.REVERSE);
               claw.setPosition(1);
               telemetry.addLine("opening claw");
           }
           else if (button_2y == true && button_2a == false){
               claw.setDirection(Servo.Direction.FORWARD);
               claw.setPosition(0.5);
               telemetry.addLine("closing claw");
           }

           if (button_2b && !button_2bPressed) {
               button_2bPressed = true;
               if (lift_position == 1) {
                   lift_position = 0;
               }
               else {
                   lift_position = 1;
               }

               arm_lift.setPosition(lift_position);
           }
           else if (!button_2b && button_2bPressed) {
                button_2bPressed = false;
            }



            //switch between field and robot centric - untested

            /* if (button_1b == true) {
                fieldCentric = true;
            } else if (button_1x == true){
                fieldCentric = false;
            } */


            //rotator with motor - working; too fast?

            if (bumper_left == true){
                rotator.setPower(0.25);
            } else if (bumper_right == true){
                rotator.setPower(-0.25);
            } else if (bumper_left == false && bumper_right == false){
                rotator.setPower(0);
            }


            //move arm using encoders

            if (dpadUp){
            arm_rt.setTargetPosition(85);
            arm_lt.setTargetPosition(85);
            arm_rt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_lt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_rt.setPower(0.25);
            arm_lt.setPower(0.25);

            /* while(arm_rt.isBusy() || arm_lt.isBusy()){
                telemetry.addData("right motor is busy", arm_rt.isBusy());
                telemetry.addData("left motor is busy", arm_lt.isBusy());
                telemetry.addData("arm left position", arm_lt.getCurrentPosition());
                telemetry.addData("arm right position", arm_rt.getCurrentPosition());
                telemetry.update();
            }

            arm_rt.setPower(0);
            arm_lt.setPower(0); */
            }

            else if (dpadDown){
            arm_rt.setTargetPosition(0);
            arm_lt.setTargetPosition(0);
            arm_rt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_lt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm_rt.setPower(0.25);
            arm_lt.setPower(0.25);

            /* while(arm_rt.isBusy() || arm_lt.isBusy()){
                telemetry.addData("right motor is busy", arm_rt.isBusy());
                telemetry.addData("left motor is busy", arm_lt.isBusy());
                telemetry.addData("arm left position", arm_lt.getCurrentPosition());
                telemetry.addData("arm right position", arm_rt.getCurrentPosition());
                telemetry.update();
            } */

            arm_rt.setPower(0);
            arm_lt.setPower(0);
            }



            //run arm w/o encoders - less control, no holding

           /*  if (dpadUp == true){
                arm_rt.setPower(0.9);
                arm_lt.setPower(0.9);
            } else if (dpadDown == true){
                arm_rt.setPower(-0.9);
                arm_lt.setPower(-0.96);
            } else if (dpadDown == false && dpadUp == false) {
                arm_rt.setPower(0);
                arm_lt.setPower(0);
            } */

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
            bk_lt.setPower(bk_lt_power * powerFactor);
            ft_lt.setPower(ft_lt_power * powerFactor);
            ft_rt.setPower(ft_rt_power * powerFactor);
            bk_rt.setPower(bk_rt_power * powerFactor);

            telemetry.addData("Heading (degrees)", " %.1f", botHeading * 180.0 / Math.PI);
            telemetry.addData("Left Stick X", left_stick_x);
            telemetry.addData("Left Stick Y", left_stick_y);
            telemetry.addData("Triggers", triggers);
            telemetry.addData("dpadUp", dpadUp);
            telemetry.addData("dpadDown", dpadDown);
            telemetry.addData("2y", button_2y);
            telemetry.addData("2a", button_2a);
            telemetry.addData("arm left position", arm_lt.getCurrentPosition());
            telemetry.addData("arm right position", arm_rt.getCurrentPosition());
            telemetry.addData("mode", arm_rt.getMode());
            telemetry.addData("arm left target pos", arm_lt.getTargetPosition());
            telemetry.addData("power left arm", arm_lt.getPower());
            telemetry.addData("power right arm", arm_rt.getPower());
            telemetry.addData("zero behavior left", arm_lt.getZeroPowerBehavior());
            telemetry.addData("zero behavior right", arm_rt.getZeroPowerBehavior());
            telemetry.addData( "arm_lift", arm_lift.getPosition());
            telemetry.addData("lift position", lift_position);
            telemetry.update();
        }
        bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0);
    }
}

