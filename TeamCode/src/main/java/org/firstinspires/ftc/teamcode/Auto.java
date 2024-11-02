package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;

@Autonomous(name = "Auto")
public class Auto extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

//Drives forward ~60 inches
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .lineToX(50, new TranslationalVelConstraint(30))
                        .build());
    }
}
