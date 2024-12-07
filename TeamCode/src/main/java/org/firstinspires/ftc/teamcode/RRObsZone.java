package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RRObsZone")
public class RRObsZone extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Riser myRiser = new Riser(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);

        TrajectoryActionBuilder drive_forward = drive.actionBuilder(beginPose).lineToY(35);
        TrajectoryActionBuilder drive_to_bar = drive_forward.endTrajectory().fresh().lineToY(30);
        TrajectoryActionBuilder drive_to_park = drive_to_bar.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(-55, 60));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(myClaw.setPosition(Claw.ClawPosition.CLOSE),
                        drive_forward.build(),
                        myRiser.setPosition(Riser.RiserPosition.HIGH_BAR),
                        drive_to_bar.build(),
                        myRiser.setPosition(Riser.RiserPosition.BOTTOM),
                        drive_to_park.build()));
    }
}