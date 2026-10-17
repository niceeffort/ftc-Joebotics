package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
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
            // Start pusher
            Actions.runBlocking(myPusher.setPosition(Pusher.PusherPosition.GO));
            // Start launcher
            Actions.runBlocking(myLauncher.setPosition(Launcher.LauncherPosition.GO));
            // TODO: Check how long this needs to sleep
            // Stop pusher
            Actions.runBlocking(myPusher.setPosition(Pusher.PusherPosition.STOP));
            // Stop launcher
            Actions.runBlocking(myLauncher.setPosition(Launcher.LauncherPosition.STOP));
            // Drive forward 25in
            robot.drive(25, 0.60, .25);
            // Drive right 55in
            // TODO: Check if this goes left or right
            robot.strafe(-55, 0.60, .25);
            // Drive forward 50in
            robot.drive(50, 0.60, .25);
        }
    }
}
