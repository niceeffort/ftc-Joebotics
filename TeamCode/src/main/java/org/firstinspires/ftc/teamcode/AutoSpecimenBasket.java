package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoSpecimenBasket")
public class AutoSpecimenBasket extends LinearOpMode {

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

        TrajectoryActionBuilder driveLeft = driveToBar.endTrajectory().fresh().lineToY(50)
                .setTangent(Math.toRadians(0)).lineToX(48);

        TrajectoryActionBuilder faceBasket = driveLeft.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90)).turnTo(Math.toRadians(45));

        TrajectoryActionBuilder driveToBasket = faceBasket.endTrajectory().fresh()
                .lineToXConstantHeading(50);

        TrajectoryActionBuilder faceSubmersible = driveToBasket.endTrajectory().fresh()
                .setTangent(Math.toRadians(45)).turnTo(Math.toRadians(180));

        TrajectoryActionBuilder driveToSubmersible = faceSubmersible.endTrajectory().fresh()
                .setTangent(Math.toRadians(90)).lineToY(10)
                .setTangent(Math.toRadians(0)).lineToX(25);

        waitForStart();

        Actions.runBlocking(new SequentialAction(
                driveForward.build(),
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR),
                driveToBar.build(),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                driveLeft.build(),
                myClaw.setPosition(Claw.ClawPosition.OPEN),
                myArm.setPosition(Arm.ArmPosition.DOWN),
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                myArm.setPosition(Arm.ArmPosition.UP),
                faceBasket.build(),
                myRiser.setPosition(Riser.RiserPosition.TOP),
                driveToBasket.build(),
                myArm.setPosition(Arm.ArmPosition.DOWN),
                myClaw.setPosition(Claw.ClawPosition.OPEN),
                myClaw.setPosition(Claw.ClawPosition.CLOSE),
                myArm.setPosition(Arm.ArmPosition.UP),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                faceSubmersible.build(),
                driveToSubmersible.build(),
                myRiser.setPosition(Riser.RiserPosition.TOP),
                myArm.setPosition(Arm.ArmPosition.DOWN)
        ));
    }
}
