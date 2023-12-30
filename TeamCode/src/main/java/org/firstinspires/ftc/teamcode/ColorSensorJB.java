package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensorJB {

    HardwareMap hardwareMap;
    Telemetry telemetry;
    ColorSensor colorSensor;
    int color;

    public ColorSensorJB(LinearOpMode opMode){
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        colorSensor = hardwareMap.colorSensor.get("color");
    }

    public int Update(){

        int red;
        int green;
        int blue;
        double colorTotal;

        double redPercent;
        double greenPercent;
        double bluePercent;
        double colorThreshold = .4;

        red = colorSensor.red();
        green = colorSensor.green();
        blue = colorSensor.blue();
        colorTotal = red + green + blue;
        redPercent = red / colorTotal;
        greenPercent = green / colorTotal;
        bluePercent = blue / colorTotal;

        telemetry.addData("red", redPercent);
        telemetry.addData("green", greenPercent);
        telemetry.addData("blue", bluePercent);

        if(redPercent >= colorThreshold){
            color = 1;
            telemetry.addLine("seeing red");
        }
        else if(greenPercent >= colorThreshold){
            color = 2;
            telemetry.addLine("seeing green");
        }
        else if(bluePercent >= colorThreshold){
            color = 3;
            telemetry.addLine("seeing blue");
        }
        else{
            color = 0;
            telemetry.addLine("seeing nothing");
        }

        telemetry.update();

        return color;
    }
}