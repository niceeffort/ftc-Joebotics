package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous (name = "AscentZone")
public class AscentZone extends LinearOpMode {

    //WhiteMecanumDrive myDrive = null;

    @Override
    public void runOpMode() throws InterruptedException{

        //myDrive = new WhiteMecanumDrive(this);

        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor lwr_arm_left = hardwareMap.dcMotor.get("lwr_arm_left");
        DcMotor lwr_arm_right = hardwareMap.dcMotor.get("lwr_arm_right");
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        //double power = .5;
        boolean done = false;
        boolean doneForward = false;
        boolean doneSide = false;
        waitForStart();

        while(opModeIsActive() && !doneSide){
            bk_rt.setPower(-0.5);
            bk_lt.setPower(0.5);
            ft_rt.setPower(0.5);
            ft_lt.setPower(-0.5);

            sleep(2500);
            doneSide = true;
        }


        while(opModeIsActive() && doneSide && !doneForward){
            bk_rt.setPower(0.5);
            bk_lt.setPower(0.5);
            ft_rt.setPower(0.5);
            ft_lt.setPower(0.5);

            sleep(500);
            doneForward = true;
        }

        while(opModeIsActive() && doneSide && doneForward && !done){
            lwr_arm_left.setPower(1);
            lwr_arm_right.setPower(1);

            sleep(1000);
            done = true;
        }

        bk_rt.setPower(0);
        bk_lt.setPower(0);
        ft_rt.setPower(0);
        ft_lt.setPower(0);
    }
}

