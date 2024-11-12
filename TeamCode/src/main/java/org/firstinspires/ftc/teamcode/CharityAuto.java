package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "CharityAuto")
public class CharityAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //To do: Change motor names to what you have in your code
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");

        //To do: Reverse motors as needed
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        bk_lt.setPower(1);
        ft_lt.setPower(1);
        ft_rt.setPower(1);
        bk_rt.setPower(1);
    }
}
