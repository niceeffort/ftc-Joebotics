package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RoadRunnerObsBlue")
public class RoadRunnerObsBlue extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, -60, Math.toRadians(-90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

        Arm myArm = new Arm(hardwareMap);
        //Intake myIntake = new Intake(hardwareMap);


        TrajectoryActionBuilder hang_specimen = drive.actionBuilder(beginPose).lineToY(-36);
        TrajectoryActionBuilder net_zone = hang_specimen.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(-55,-56));
        //check pos on coordinate plane
        TrajectoryActionBuilder obs_zone_park = net_zone.endTrajectory().fresh().strafeToConstantHeading(new Vector2d(50,-56));

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(myArm.setPosition(Arm.ArmPos.ARM_SCORE),
                        hang_specimen.build(),
                        obs_zone_park.build(),
                        myArm.setPosition(Arm.ArmPos.DOWN)));
    }
}
