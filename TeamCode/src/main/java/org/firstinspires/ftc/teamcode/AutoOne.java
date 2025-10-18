package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="AutoOne")
public class AutoOne extends LinearOpMode{
    private SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override public void runOpMode(){

        robot.initialize(true);

        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()){
            robot.drive(-56, 0.5, 0.25);
        }
    }
}