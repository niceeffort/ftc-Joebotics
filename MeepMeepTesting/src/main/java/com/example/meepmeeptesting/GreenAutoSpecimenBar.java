package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GreenAutoSpecimenBar {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        Pose2d beginPose = new Pose2d(10, 60, Math.toRadians(-90));
        DriveShim drive = myBot.getDrive();

        TrajectoryActionBuilder driveForward = drive.actionBuilder(beginPose).lineToY(35);

        TrajectoryActionBuilder driveToBar = driveForward.endTrajectory().fresh().
                lineToY(30);

        TrajectoryActionBuilder driveLeft = driveToBar.endTrajectory().fresh().lineToY(35)
                .setTangent(Math.toRadians(0)).lineToX(38);

        TrajectoryActionBuilder driveToSubmersible = driveLeft.endTrajectory().fresh()
                .setTangent(Math.toRadians(-90)).lineToY(2).turnTo(Math.toRadians(180))
                .setTangent(Math.toRadians(0)).lineToX(25);



        myBot.runAction(new SequentialAction(
                driveForward.build(),
                driveToBar.build(),
                driveLeft.build(),
                driveToSubmersible.build()
                ));

        Image img = null;
        String filename = "field-2024-juice-dark.png";
        try { img = ImageIO.read(new File("MeepMeepTesting/" + filename)); }
        catch(IOException e) {
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