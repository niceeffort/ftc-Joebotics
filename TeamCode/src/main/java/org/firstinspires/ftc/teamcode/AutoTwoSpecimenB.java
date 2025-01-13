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

@Autonomous(name = "AutoTwoSpecimenB")
public class AutoTwoSpecimenB extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Riser myRiser = new Riser(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);
        Arm myArm = new Arm(hardwareMap);

        TrajectoryActionBuilder driveForward = drive.actionBuilder(beginPose).lineToY(30);
        //TrajectoryActionBuilder driveToBar = driveForward.endTrajectory().fresh().
        //        lineToY(30);

        TrajectoryActionBuilder driveToPickup = driveForward.endTrajectory().fresh().
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
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR)));

        Actions.runBlocking(new SequentialAction(
                driveForward.build(),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                driveToPickup.build()));

        Actions.runBlocking(new ParallelAction(
                myClaw.setPosition(Claw.ClawPosition.OPEN),
                myArm.setPosition(Arm.ArmPosition.DOWN)));

        Actions.runBlocking(new SequentialAction(
                new SleepAction(1),
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                new SleepAction(2)));

        Actions.runBlocking(new ParallelAction(
                myArm.setPosition(Arm.ArmPosition.UP),
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR)));

        Actions.runBlocking(new SequentialAction(
                driveBackToBar.build(),
                driveToBarTwo.build(),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                driveToPark.build()));
    }
}