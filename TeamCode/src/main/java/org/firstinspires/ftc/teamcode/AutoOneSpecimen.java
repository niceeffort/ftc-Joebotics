package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoOneSpecimen")
public class AutoOneSpecimen extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d beginPose = new Pose2d(10, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Riser myRiser = new Riser(hardwareMap);
        Arm myArm = new Arm(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);

        TrajectoryActionBuilder driveForward = drive.actionBuilder(beginPose).lineToY(35);

        TrajectoryActionBuilder driveToBar = driveForward.endTrajectory().fresh().
                lineToY(30);

        TrajectoryActionBuilder driveLeft = driveToBar.endTrajectory().fresh().lineToY(35)
                .setTangent(Math.toRadians(0)).lineToX(40);

        TrajectoryActionBuilder driveToSubmersible = driveLeft.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90)).lineToY(2).turnTo(Math.toRadians(180))
                .setTangent(Math.toRadians(0)).lineToX(30);

        waitForStart();

        Actions.runBlocking(new SequentialAction(
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                driveForward.build(),
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR),
                driveToBar.build(),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                driveLeft.build(),
                driveToSubmersible.build(),
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR),
                myArm.setPosition(Arm.ArmPosition.DOWN)
        ));
    }
}
