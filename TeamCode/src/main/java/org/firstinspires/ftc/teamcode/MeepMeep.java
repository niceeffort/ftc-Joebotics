package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "MeepMeep")
public class MeepMeep extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(35, 60, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .waitSeconds(1)
                        .setTangent(0)
                        .lineToX(0)
                        .waitSeconds(.5)
                        .turnTo(Math.toRadians(270))
                        .waitSeconds(.5)
                        .lineToY(35)
                        .lineToY(60)
                        .turnTo(0)
                        .waitSeconds(.5)
                        .lineToX(35)
                        .waitSeconds(.5)
                        .strafeToConstantHeading(new Vector2d(48, 25))
                        .waitSeconds(.5)
                        .strafeToConstantHeading(new Vector2d(35, 60))
                        .waitSeconds(.5)
                        .strafeToConstantHeading(new Vector2d(61, 25))
                        .waitSeconds(.5)
                        .strafeToConstantHeading(new Vector2d(61, 60))
                        .build());
    }
}