package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public class DriveBotTestAuto extends LinearOpMode {

    @Override public void runOpMode() throws InterruptedException {

        // Get an instance of the robot class
        final SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);

        // Telemetry telling driver to press start
        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();  // Reset heading to set a baseline for Auto

        // Run auto if stop was not pressed
        if (opModeIsActive()) {
            robot.drive(10, .1, .25);
            robot.turnTo(90);
        }
    }
}
