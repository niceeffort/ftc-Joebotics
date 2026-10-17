package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "BioBuzzAuto2")
public class BioBuzzAuto2 extends LinearOpMode {

    // get an instance of the "Robot" class.
    private final SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override public void runOpMode() throws InterruptedException{
        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);

        //Get arm and lift
        Launcher myLauncher = new Launcher(hardwareMap);
        Pusher myPusher = new Pusher(hardwareMap);

        // Wait for driver to press start
        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();  // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        if (opModeIsActive()){
            // Start launcher
            myLauncher.setPower(0.3);

            // Warm up for half a second
            sleep(500);

            // Start pusher
            myPusher.setPower(0.3);

            // Keep pusher running for 7.5 seconds
            sleep(7500);

            // Stop pusher
            myPusher.setPower(0.0);

            // Stop launcher
            myLauncher.setPower(0.0);

            // Drive forward 25in
            robot.drive(-25, 0.60, .25);

            // Drive right 55in
            // TODO: Check if this goes left or right
            robot.strafe(55, 0.60, .25);

            // Drive forward 50in
            robot.drive(-50, 0.60, .25);
        }
    }
}
