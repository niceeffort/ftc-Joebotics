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

        //Pitcher code
        if (pitcherGo) {
            ft_pr.setPower(.5);
            bk_pr.setPower(.5);
        } else if (pitcherStop) {
            ft_pr.setPower(0.0);
            bk_pr.setPower(0.0);
        }

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

            //Drive backward
            bk_lt.setPower(.5);
            bk_rt.setPower(.5);
            ft_lt.setPower(.5);
            ft_rt.setPower(.5);
            sleep(1050);

            //Shoot 1st ball
            cam.setPosition(CamPosition.GO.ordinal());
            ft_pr.setPower(pitcherGo);
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