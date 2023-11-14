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
        int colorThreshold = 20;

        ColorSensor colorSensor = hardwareMap.colorSensor.get("color");
        waitForStart();
        while (opModeIsActive()){

            red = colorSensor.red();
            green = colorSensor.green();
            blue = colorSensor.blue();

            telemetry.addData("red", red);
            telemetry.addData("green", green);
            telemetry.addData("blue", blue);

            if(red >= colorThreshold){
                telemetry.addLine("seeing red");
            }
            else if(blue >= colorThreshold){
                telemetry.addLine("seeing blue");
            }
            else if(green >= colorThreshold){
                telemetry.addLine("seeing green");
            }
            else{
                telemetry.addLine("seeing nothing");
            }

            telemetry.update();

        }
    }
}
