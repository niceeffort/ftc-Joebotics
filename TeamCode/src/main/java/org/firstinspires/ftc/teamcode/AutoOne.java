package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="AutoOne")
public class AutoOne extends LinearOpMode{
    private SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override public void runOpMode(){

        robot.initialize(true);

        DcMotor ft_pr = hardwareMap.dcMotor.get("front_pitcher");
        DcMotor bk_pr = hardwareMap.dcMotor.get("back_pitcher");
        CRServo cam = hardwareMap.crservo.get("cam");

        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()){
            robot.drive(-55, 0.5, 0.25); //Drive backward
            cam.setPower(.5); //Turn on cam

            //Shoot first ball
            ft_pr.setPower(.5);
            bk_pr.setPower(.5);
            sleep(1000);
            ft_pr.setPower(0);
            bk_pr.setPower(0);
            sleep(1000);

            // Shoot second ball
            ft_pr.setPower(.5);
            bk_pr.setPower(.5);
            sleep(1000);
            ft_pr.setPower(0);
            bk_pr.setPower(0);
            sleep(1000);

            //Shoot third ball
            ft_pr.setPower(.5);
            bk_pr.setPower(.5);
            sleep(1000);
            ft_pr.setPower(0);
            bk_pr.setPower(0);
            sleep(1000);
        }
    }
}