package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous (name = "SimpleAuto2425")
public class SimpleAuto2425ObsZone extends LinearOpMode {

    //WhiteMecanumDrive myDrive = null;

    @Override
    public void runOpMode() throws InterruptedException{

        //myDrive = new WhiteMecanumDrive(this);

        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        //double power = .5;
        boolean done = false;
        waitForStart();

        while(opModeIsActive() && !done){
            bk_rt.setPower(0.5);
            bk_lt.setPower(-0.5);
            ft_rt.setPower(-0.5);
            ft_lt.setPower(0.5);

            sleep(3000);
            done = true;
        }

        bk_rt.setPower(0);
        bk_lt.setPower(0);
        ft_rt.setPower(0);
        ft_lt.setPower(0);
    }
}
