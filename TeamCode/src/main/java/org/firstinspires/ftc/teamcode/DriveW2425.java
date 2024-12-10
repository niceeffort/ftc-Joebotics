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

    final double ARM_TICKS_PER_DEGREE =
            28 // number of encoder ticks per rotation of the bare motor
                    * 250047.0 / 4913.0 // This is the exact gear ratio of the 50.9:1 Yellow Jacket gearbox
                    * 100.0 / 20.0 // This is the external gear reduction, a 20T pinion gear that drives a 100T hub-mount gear
                    * 1/360.0; // we want ticks per degree, not per rotation

        /* In these variables you'll see a number in degrees, multiplied by the ticks per degree of the arm.
        This results in the number of encoder ticks the arm needs to move in order to achieve the ideal
        set position of the arm. For example, the ARM_SCORE_SAMPLE_IN_LOW is set to
        160 * ARM_TICKS_PER_DEGREE. This asks the arm to move 160° from the starting position.
        If you'd like it to move further, increase that number. If you'd like it to not move
        as far from the starting position, decrease it. */

    final double ARM_COLLAPSED_INTO_ROBOT  = 0;
    final double ARM_COLLECT               = 250 * ARM_TICKS_PER_DEGREE;
    final double ARM_CLEAR_BARRIER         = 230 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SPECIMEN        = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_SCORE_SAMPLE_IN_LOW   = 160 * ARM_TICKS_PER_DEGREE;
    final double ARM_ATTACH_HANGING_HOOK   = 120 * ARM_TICKS_PER_DEGREE;
    final double ARM_WINCH_ROBOT           = 15  * ARM_TICKS_PER_DEGREE;

    /* Variables to store the speed the intake servo should be set at to intake, and deposit game elements. */
    final double INTAKE_COLLECT    = -1.0;
    final double INTAKE_OFF        =  0.0;
    final double INTAKE_DEPOSIT    =  0.5;

    /* Variables to store the positions that the wrist should be set to when folding in, or folding out. */
    final double WRIST_FOLDED_IN   = 0.8333;
    final double WRIST_FOLDED_OUT  = 0.5;

    /* A number in degrees that the triggers can adjust the arm position by */
    final double FUDGE_FACTOR = 15 * ARM_TICKS_PER_DEGREE;

    /* Variables that are used to set the arm to a specific position */
    double armPosition = (int)ARM_COLLAPSED_INTO_ROBOT;
    double armPositionFudgeFactor;


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
        //DcMotor lwr_arm_left = hardwareMap.dcMotor.get("lwr_arm_left");
        //DcMotor lwr_arm_right = hardwareMap.dcMotor.get("lwr_arm_right");
        //DcMotor upr_arm = hardwareMap.dcMotor.get("upr_arm");
        DcMotor arm = hardwareMap.dcMotor.get("arm");
        //Servo claw = hardwareMap.servo.get("claw");
        Servo wrist = hardwareMap.servo.get("wrist");
        CRServo intake  = hardwareMap.get(CRServo.class, "intake");
        //CRServo wrist = hardwareMap.get(CRServo.class, "wrist");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // This part may be robot dependant
       /* bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);
        lwr_arm_right.setDirection((DcMotorSimple.Direction.REVERSE));
        lwr_arm_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lwr_arm_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        upr_arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); */

        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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
            boolean bumperL = gamepad2.left_bumper;
            boolean bumperR = gamepad2.right_bumper;
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
            /*if (dpad_up) {
                upr_arm.setPower(0.75);
            } else if (dpad_down) {
                upr_arm.setPower(-0.75);
            } else {
                upr_arm.setPower(0);
            } */

            /* // lower arm movement
            if (buttonA) {
                lwr_arm_left.setPower(0.75);
                lwr_arm_right.setPower(0.75);
            } else if (buttonY) {
                lwr_arm_left.setPower(-0.5);
                lwr_arm_right.setPower(-0.5);
            } else {
                lwr_arm_left.setPower(0);
                lwr_arm_right.setPower(0);
            } */

            if(gamepad1.right_bumper){
                /* This is the intaking/collecting arm position */
                armPosition = ARM_COLLECT;
                wrist.setPosition(WRIST_FOLDED_OUT);
                intake.setPower(INTAKE_COLLECT);
            }

            else if (gamepad1.left_bumper){
                    /* This is about 20° up from the collecting position to clear the barrier
                    Note here that we don't set the wrist position or the intake power when we
                    select this "mode", this means that the intake and wrist will continue what
                    they were doing before we clicked left bumper. */
                armPosition = ARM_CLEAR_BARRIER;
            }

            else if (gamepad1.y){
                /* score the sample in the LOW BASKET */
                armPosition = ARM_SCORE_SAMPLE_IN_LOW;
            }

            else if (gamepad1.dpad_left) {
                    /* off intake, fold wrist, move the arm
                    to folded in robot - starting configuration */
                armPosition = ARM_COLLAPSED_INTO_ROBOT;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_right){
                /* score SPECIMEN on the HIGH CHAMBER */
                armPosition = ARM_SCORE_SPECIMEN;
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_up){
                /* arm to vertical to hook onto the LOW RUNG */
                armPosition = ARM_ATTACH_HANGING_HOOK;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }

            else if (gamepad1.dpad_down){
                /* arm down to lift the robot */
                armPosition = ARM_WINCH_ROBOT;
                intake.setPower(INTAKE_OFF);
                wrist.setPosition(WRIST_FOLDED_IN);
            }


           /* // claw movement
            if (bumperL) {
               claw.setPosition(0.5);

              //claw.setPower(0.2);
            } else if (bumperR) {
               claw.setPosition(0);

               //claw.setPower(-0.2);
            }  else {
               //claw.setPower(0);
            } */

            /* //wrist
            if (buttonB) {
                wrist.setPosition(1);
                //wrist.setPower(0.2);
            } else if (buttonX) {
                wrist.setPosition(0.4);
                //wrist.setPower(-0.2);
            } /* else {
                //wrist.setPower(0);
            } */

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
