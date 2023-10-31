package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
@Autonomous(name = "AutonomusBackstage")
public class AutonomusBackstage extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor bk_rt = hardwareMap.dcMotor.get("bk_rt");
        DcMotor bk_lt = hardwareMap.dcMotor.get("bk_lt");
        DcMotor ft_rt = hardwareMap.dcMotor.get("ft_rt");
        DcMotor ft_lt = hardwareMap.dcMotor.get("ft_lt");
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        double power = .5;
        boolean done = false;
        waitForStart();

        while(opModeIsActive() && !done){
           bk_rt.setPower(power);
           bk_lt.setPower(power);
           ft_rt.setPower(power);
           ft_lt.setPower(power);

           sleep(1000);
           done = true;
        }

        bk_rt.setPower(0);
        bk_lt.setPower(0);
        ft_rt.setPower(0);
        ft_lt.setPower(0);

    }
}
