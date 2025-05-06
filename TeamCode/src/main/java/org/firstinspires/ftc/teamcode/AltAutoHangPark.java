package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AltAutoHangPark")
public class AltAutoHangPark extends LinearOpMode {

    // get an instance of the "Robot" class.
    private final SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override public void runOpMode() throws InterruptedException{
        // Initialize the robot hardware & Turn on telemetry
        robot.initialize(true);

        //Get arm and lift
        Lift myLift = new Lift(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);

        // Wait for driver to press start
        telemetry.addData(">", "Touch Play to run Auto");
        telemetry.update();

        waitForStart();
        robot.resetHeading();  // Reset heading to set a baseline for Auto

        // Run Auto if stop was not pressed.
        //TODO: Measure distances and put in correct values
        if (opModeIsActive()){
            Actions.runBlocking(myClaw.setPosition(Claw.ClawPosition.CLOSE));
            //Drive forward
            robot.drive(28,0.60,0.25);
            Actions.runBlocking(myLift.setPosition(Lift.LiftPosition.HIGH_BAR));
            //Drive to bar
            robot.drive(2,0.60,0.25);
            Actions.runBlocking(myLift.setPosition(Lift.LiftPosition.BOTTOM));
            //Drive to park
            robot.strafe(-39,0.60,0.25);
            robot.drive(-39,0.60,0.25);
        }
    }
}
