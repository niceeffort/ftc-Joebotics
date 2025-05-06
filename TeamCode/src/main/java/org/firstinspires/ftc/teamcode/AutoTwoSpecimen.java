package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoTwoSpecimen")
public class AutoTwoSpecimen extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Lift myLift = new Lift(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);
        Arm myArm = new Arm(hardwareMap);

        TrajectoryActionBuilder driveForward = drive.actionBuilder(beginPose).lineToY(30);
        TrajectoryActionBuilder driveToBar = driveForward.endTrajectory().fresh().
                lineToY(30);

        TrajectoryActionBuilder driveToPickup = driveToBar.endTrajectory().fresh().
                strafeToConstantHeading(new Vector2d(-65, 35)).
                turnTo(Math.toRadians(90));

        TrajectoryActionBuilder driveBackToBar = driveToPickup.endTrajectory().fresh().
                turnTo(Math.toRadians(-90)).
                strafeToConstantHeading(new Vector2d(5, 35));

        TrajectoryActionBuilder driveToBarTwo = driveBackToBar.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90))
                .lineToY(30);

        TrajectoryActionBuilder driveToPark = driveToBarTwo.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(-55, 60));

        waitForStart();

        Actions.runBlocking(new ParallelAction(
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                myLift.setPosition(Lift.LiftPosition.HIGH_BAR),
                driveForward.build()));

        Actions.runBlocking(new SequentialAction(
                myLift.setPosition(Lift.LiftPosition.BOTTOM)));

        Actions.runBlocking(new ParallelAction(
                myClaw.setPosition(Claw.ClawPosition.OPEN),
                driveToPickup.build(),
                myArm.setPosition(Arm.ArmPosition.DOWN)));

        Actions.runBlocking(new SequentialAction(
                new SleepAction(2),
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                new SleepAction(2)));

        Actions.runBlocking(new ParallelAction(
                myArm.setPosition(Arm.ArmPosition.UP),
                driveBackToBar.build(),
                myLift.setPosition(Lift.LiftPosition.HIGH_BAR)));

        Actions.runBlocking(new SequentialAction(
                driveToBarTwo.build(),
                myLift.setPosition(Lift.LiftPosition.BOTTOM),
                driveToPark.build()));

    }
}