package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoBasketSub")
public class AutoBasketSub extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Riser myRiser = new Riser(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);
        Arm myArm = new Arm(hardwareMap);

        TrajectoryActionBuilder driveToBasket = drive.actionBuilder(beginPose)
                .setTangent(Math.toRadians(0)).lineToX(48);

        TrajectoryActionBuilder driveToSub = driveToBasket.endTrajectory().fresh()
                .setTangent(Math.toRadians(90)).lineToY(55).turnTo(Math.toRadians(180))
                .setTangent(Math.toRadians(90)).lineToY(0);

        TrajectoryActionBuilder touchBar = driveToSub.endTrajectory().fresh()
                .setTangent(Math.toRadians(0)).lineToX(30);

        TrajectoryActionBuilder upToBar = touchBar.endTrajectory().fresh()
                .lineToX(25);

        waitForStart();

        Actions.runBlocking(new SequentialAction(
                driveToBasket.build(),
                myRiser.setPosition(Riser.RiserPosition.HIGH_BAR),
                myArm.setPosition(Arm.ArmPosition.DOWN),
                myClaw.setPosition(Claw.ClawPosition.OPEN),
                myArm.setPosition(Arm.ArmPosition.UP),
                myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                driveToSub.build(),
                touchBar.build(),
                upToBar.build()
        ));

    }
}
