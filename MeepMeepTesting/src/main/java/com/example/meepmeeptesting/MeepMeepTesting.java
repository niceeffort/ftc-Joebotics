package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(35, 60, 0))
                .waitSeconds(1)
                .setTangent(0)
                .splineTo(new Vector2d(0, 35), Math.toRadians(270))
                .waitSeconds(.5)
                .lineToY(50)
                .turnTo(0)
               // .strafeToConstantHeading(new Vector2d(35, 25))
                .splineTo(new Vector2d(35, 25), Math.toRadians(0))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(35, 60))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(48, 25))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(35, 60))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(61, 25))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(61, 60))


                .build());

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