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
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "AutoTwoSpecimenC")
public class AutoTwoSpecimenC extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        //Reset motor encoders
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        bk_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bk_lt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor fr_lt = hardwareMap.dcMotor.get("front_left_motor");
        fr_lt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr_lt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor fr_rt = hardwareMap.dcMotor.get("front_right_motor");
        fr_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fr_rt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        bk_rt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bk_rt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Lift myLift = new Lift(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);
        Arm myArm = new Arm(hardwareMap);

        TrajectoryActionBuilder driveForward = drive.actionBuilder(beginPose).lineToY(32);

        TrajectoryActionBuilder driveToPickup = driveForward.endTrajectory().fresh().
                strafeToConstantHeading(new Vector2d(-55, 35)).
                turnTo(Math.toRadians(90));
        
        TrajectoryActionBuilder driveBackToBar = driveToPickup.endTrajectory().fresh().
                turnTo(Math.toRadians(-90)).
                strafeToConstantHeading(new Vector2d(5, 35));

        TrajectoryActionBuilder driveToBarTwo = driveBackToBar.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90))
                .lineToY(32);

        TrajectoryActionBuilder driveToPark = driveToBarTwo.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(-55, 60));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                    new ParallelAction(
                        myClaw.setPosition(Claw.ClawPosition.CLOSE),
                        myLift.setPosition(Lift.LiftPosition.HIGH_BAR)),

                    new SequentialAction(
                        driveForward.build(),
                        myLift.setPosition(Lift.LiftPosition.BOTTOM),
                        driveToPickup.build()),

                    new ParallelAction(
                        myClaw.setPosition(Claw.ClawPosition.OPEN),
                        myArm.setPosition(Arm.ArmPosition.DOWN)),

                    new SequentialAction(
                        new SleepAction(1),
                        myClaw.setPosition(Claw.ClawPosition.CLOSE),
                        new SleepAction(1)),

                    new ParallelAction(
                        myArm.setPosition(Arm.ArmPosition.UP),
                        myLift.setPosition(Lift.LiftPosition.HIGH_BAR)),

                    new SequentialAction(
                        driveBackToBar.build(),
                        driveToBarTwo.build(),
                        myLift.setPosition(Lift.LiftPosition.BOTTOM),
                        driveToPark.build())));
    }
}