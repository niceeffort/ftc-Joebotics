package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="AutoOne")
public class AutoOne extends LinearOpMode{

    @Override public void runOpMode(){

        // DS NOTE: Why not use the MecanumDriveJB Class here instead? This will greatly simplify the code
        //MecanumDriveJB myDrive = new MecanumDriveJB(this);

        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor ft_pr = hardwareMap.dcMotor.get("front_pitcher");
        DcMotor bk_pr = hardwareMap.dcMotor.get("back_pitcher");
        Servo cam = hardwareMap.servo.get("cam");

        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_pr.setDirection(DcMotor.Direction.REVERSE);
        bk_pr.setDirection(DcMotor.Direction.REVERSE);
        ft_pr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bk_pr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // DS NOTE: You never declare or set pitcherGo or pitcherStop.
        // You need to set these to the power level you want to use
        final double pitcherGo = .5;
        final double pitcherStop = 0.0;

        // DS NOTE: This code doesn't make sense to me and it is only run when th opmode initializes
        //I would comment it out.
        /*
        //Pitcher code
        if (pitcherGo) {
            ft_pr.setPower(.5);
            bk_pr.setPower(.5);
        } else if (pitcherStop) {
            ft_pr.setPower(0.0);
            bk_pr.setPower(0.0);
        }*/


        // DS NOTE: I don't understand this "Cam code". I would comment it out.
        // I think you just need two variable for open and close position like this
        final double camOpen = 1.0;
        final double camClosed = 0.0;

        //Cam code
        enum CamPosition {
            GO(1.0),
            STOP(0.0);
            final double positionValue;
            CamPosition(double positionValue) {
                this.positionValue = positionValue;
            }
        }

        waitForStart();

        if (opModeIsActive()){

            // DS NOTE: Just feed in the stick values as if you were pressing them.
            // Remember that the y stick is reversed, but we handle that in MecanumDriveJB
            // This is how you would drive backwards
            //myDrive.Update(0.0, 1.0, 0.0, 0.0);

            // This is how you turn
            // myDrive.Update(0.0, 0.0, 1.0, 0.0);

            //Drive backward
            bk_lt.setPower(.5);
            bk_rt.setPower(.5);
            ft_lt.setPower(.5);
            ft_rt.setPower(.5);
            sleep(1050);



            //Shoot 1st ball

            // DS NOTE: I think you can just set it like this
            // cam.setPosition(camOpen);
            cam.setPosition(CamPosition.GO.ordinal());
            ft_pr.setPower(pitcherGo);


            // DS NOTE: IS this right? You set the power on the ft_pr and the front right wheel?
            ft_rt.setPower(pitcherGo);
            sleep(100);
            cam.setPosition(CamPosition.STOP.ordinal());
            ft_pr.setPower(pitcherStop);
            ft_rt.setPower(pitcherStop);
            sleep(100);

            //Shoot 2nd ball
            cam.setPosition(CamPosition.GO.ordinal());
            ft_pr.setPower(pitcherGo);
            ft_rt.setPower(pitcherGo);
            sleep(100);
            cam.setPosition(CamPosition.STOP.ordinal());
            ft_pr.setPower(pitcherStop);
            ft_rt.setPower(pitcherStop);
            sleep(100);

            //Shoot 3rd ball
            cam.setPosition(CamPosition.GO.ordinal());
            ft_pr.setPower(pitcherGo);
            ft_rt.setPower(pitcherGo);
            sleep(100);
            cam.setPosition(CamPosition.STOP.ordinal());
            ft_pr.setPower(pitcherStop);
            ft_rt.setPower(pitcherStop);
            sleep(100);

            //Drive off line
            ft_lt.setPower(.5);
            sleep(100);
            ft_lt.setPower(0);
            sleep(100);
            bk_lt.setPower(.5);
            bk_rt.setPower(.5);
            ft_lt.setPower(.5);
            ft_rt.setPower(.5);
            sleep(100);



        }
    }
}