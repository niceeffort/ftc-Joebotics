package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "DriveW2425")
public class DriveW2425 extends LinearOpMode{
    WhiteMecanumDrive myDrive = null;
    public CRServo  intake      = null; //the active intake servo
    public Servo    wrist       = null; //the wrist servo


    @Override
    public void runOpMode() throws InterruptedException {

        myDrive = new WhiteMecanumDrive(this);

        boolean fieldCentric = false;
        double powerFactor = 0.75;
        boolean lowPowerMode = false;
        boolean aButtonPress = false;

        DcMotor arm  = hardwareMap.get(DcMotor.class, "arm"); //the arm motor
        Servo wrist = hardwareMap.servo.get("wrist");
        CRServo intake  = hardwareMap.get(CRServo.class, "intake");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



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


            //arm
            if (dpad_up) {
                arm.setPower(0.5);
                telemetry.addLine("arm up");
                telemetry.update();
            } else if (dpad_down) {
                arm.setPower(-0.5);
                telemetry.addLine("arm down");
                telemetry.update();
            } else {
                arm.setPower(0);
            }

            //intake
            if (bumperL) {
                //intake in
                intake.setPower(1);
            } else if (bumperR) {
                //intake out
                intake.setPower(-1);
            } else {
                intake.setPower(0);
            }

            //wrist
            if (buttonX) {
                //wrist out
                wrist.setPosition(1);
            } else if (buttonB) {
                //wrist in
                wrist.setPosition(0.75);
            }


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


            /*if (gamepad1.a && !aButtonPress) {
                aButtonPress = true;
                if (lowPowerMode){
                    powerFactor = 1.0;
                    lowPowerMode = false;
                } else{
                    powerFactor = 0.25;
                    lowPowerMode = true;
                }

            } else if (!gamepad1.a && aButtonPress) {
                aButtonPress = false; */
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
