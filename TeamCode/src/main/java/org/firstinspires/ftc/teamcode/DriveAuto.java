package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="DriveAuto")
public class DriveAuto extends LinearOpMode{

    @Override public void runOpMode(){

        MecanumDriveJB myDrive = new MecanumDriveJB(this);

        // Get motors.
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");

        // Reverse and brake behavior
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        if (opModeIsActive()){

            bk_lt.setPower(.5);
            bk_rt.setPower(.5);
            ft_lt.setPower(.5);
            ft_rt.setPower(.5);
            sleep(1050);

        }
    }
}