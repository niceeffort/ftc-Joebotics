package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "ExampleBioBuzzAuto")
public class ExampleBioBuzzAuto extends LinearOpMode {

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

            /*
            Here are examples of driving.
            You may need to switch whether something is positive or negative.
            Switching the power will make the robot go faster or slower.
            I don't recommend changing the holdTime. I don't know what it does.
            In case you forget, strafing is driving left or right without turning.
             */

            // Drive forward
            robot.drive(25, 0.60, .25);
            // Drive Backward
            robot.drive(-25, 0.60, .25);
            // Strafe left
            robot.strafe(-50, 0.60, .25);
            // Strafe right
            robot.strafe(50, 0.60, .25);
            // Turn left
            robot.turnTo(90);
            //Turn right
            robot.turnTo(-90);

            /*
            Here are examples of using the launcher and pusher.
            To use a mechanic in autonomous, you need to make another class and call on it.
            For the wheels, we call on the SimplifiedOdometryRobot class.
            For other mechanics such as a launcher, pusher, or intake you need to make those classes yourself.
            You can look at the Arm, Cam, Claw, Launcher, Lift, Pitcher, and Pusher for examples on how to make such classes.
             */

            // Start launcher
            myLauncher.setPower(0.3); // The number is how much power goes to the motor
            // Start pusher
            myPusher.setPower(0.3);
            // Keep pusher running for 7.5 seconds
            sleep(7500); // Read about sleeping below
            // Stop pusher
            myPusher.setPower(0.0);
            // Stop launcher
            myLauncher.setPower(0.0);

            /*
            You can also "sleep."
            When you tell the robot to sleep it stops reading the code for however long you tell it to sleep,
            it does not stop whatever is hapening.
            Above, the sleep tells the code to keep doing what it's doing (running the launcher and pusher)
            for 7.5 seconds before doing the next lines (stopping the launcher and pusher.
             */
        }
    }
}
