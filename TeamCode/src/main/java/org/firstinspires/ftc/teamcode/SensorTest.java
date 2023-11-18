package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
@TeleOp(name = "SensorTest")
public class SensorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        int red = 0;
        int green = 0;
        int blue = 0;
        double colorTotal = 0;

        double redPercent = 0;
        double greenPercent = 0;
        double bluePercent = 0;
        double colorThreshold = .4;

        ColorSensor colorSensor = hardwareMap.colorSensor.get("color");
        waitForStart();
        while (opModeIsActive()){

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
                telemetry.addLine("seeing red");
            }
            else if(bluePercent >= colorThreshold){
                telemetry.addLine("seeing blue");
            }
            else if(greenPercent >= colorThreshold){
                telemetry.addLine("seeing green");
            }
            else{
                telemetry.addLine("seeing nothing");
            }

            telemetry.update();

        }
    }
}
