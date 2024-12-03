package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "RRObsZone")
public class RRObsZone extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException{
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Riser myRiser = new Riser(hardwareMap);
        Claw myClaw = new Claw(hardwareMap);

        waitForStart();

        //Close claw
        Actions.runBlocking(myClaw.setPosition(Claw.ClawPosition.CLOSE));

        //Drive to bar
        Actions.runBlocking(
                drive.actionBuilder(beginPose)
                        .lineToY(35)
                        .build());

        //Raise riser
        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.HIGH_BAR));

        //Drive forward more
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0,35, Math.toRadians(-90)))
                        .lineToY(30)
                        .build());

        //Lower riser
        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.DOWN));

        //Park
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0,30, Math.toRadians(-90)))
                        .strafeToConstantHeading(new Vector2d(-55, 60))
                        .build());
    }
}