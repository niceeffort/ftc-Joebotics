package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "AutoSample")
public class AutoSample extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        Claw myClaw = new Claw(hardwareMap);

        waitForStart();

//Drives forward ~60 inches
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .strafeTo(new Vector2d(0,-5))
                        .setTangent(0) // You need to set the tangent to 0 to drive forward because the strafeTo function will change it
                        .lineToX(12, new TranslationalVelConstraint(30))
                        .build());

        Actions.runBlocking(myClaw.setPosition(Claw.ClawPosition.OPEN));
        sleep(1000);

        Actions.runBlocking(drive.actionBuilder(new Pose2d(12, -10, 0))
                .lineToX(-110)
                .build());
    }
}
