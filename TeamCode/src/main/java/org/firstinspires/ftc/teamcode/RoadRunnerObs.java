package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Autonomous(name = "RoadRunnerObs")
public class RoadRunnerObs extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Arm myArm = new Arm(hardwareMap);
        //Intake mtIntake = new Intake(hardwareMap);

        TrajectoryActionBuilder net_zone = drive.actionBuilder(beginPose).lineToX(60);
        //check pos on coordinate plane
        TrajectoryActionBuilder obs_zone_park = net_zone.endTrajectory().fresh().strafeToConstantHeading();

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(net_zone.build(),
                        myArm.setPosition(Arm.ArmPos.HIGH_BAR),
                        obs_zone_park.build()));
    }
}
