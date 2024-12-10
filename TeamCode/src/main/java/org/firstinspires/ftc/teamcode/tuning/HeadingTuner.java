package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Claw;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name = "HeadingTuner")
public class HeadingTuner extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        waitForStart();

        while(opModeIsActive()) {
            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .turnTo(Math.toRadians(90))
                            .waitSeconds(1)
                            .turnTo(Math.toRadians(-90))
                            .build());
        }
    }
}