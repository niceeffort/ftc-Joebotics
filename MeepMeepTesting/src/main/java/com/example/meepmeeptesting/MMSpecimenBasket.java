package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MMSpecimenBasket {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        System.out.println("Hello World");

        // Get start time
        long startTime = System.currentTimeMillis();

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d beginPose = new Pose2d(10, 60, Math.toRadians(-90));
        DriveShim drive = myBot.getDrive();

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

        // Get end time
        long endTime = System.currentTimeMillis();

        // Calculate execution time
        long executionTime = endTime - startTime;

        System.out.println("Execution time: " + executionTime + " milliseconds");

        myBot.runAction(new SequentialAction(
                driveForward.build(),
                driveToBar.build(),
                driveLeft.build(),
                faceBasket.build(),
                driveToBasket.build(),
                faceSubmersible.build(),
                driveToSubmersible.build()
        ));

        Image img = null;
        String filename = "field-2024-juice-dark.png";
        try {
            img = ImageIO.read(new File("MeepMeepTesting/" + filename));
        } catch (IOException e) {
            System.out.println("File not found!");
            System.out.println(System.getProperty("user.dir"));
        }

        meepMeep.setBackground(img);

        //meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
