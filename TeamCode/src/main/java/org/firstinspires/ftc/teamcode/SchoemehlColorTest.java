package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "SchoemehlColorTest")
public class SchoemehlColorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        ColorSensor colorSensor = hardwareMap.colorSensor.get("color");
        // Idea: Try calculating the normalized value and then check the distance to that
        // Get some sensor reading to see if this would make sense.
        Color blue_match = new Color(95, 170, 250);
        blue_match = NormalizeColor(blue_match);
        Color red_match = new Color( 280, 170, 90);
        red_match = NormalizeColor(red_match);
        Color[] matches = {red_match, blue_match};
        String[] colors = {"Red", "Blue","none"};


        waitForStart();
        while(opModeIsActive()) {
            int red = colorSensor.red();
            int green = colorSensor.green();
            int blue = colorSensor.blue();
            Color sensorColor = new Color(red, green, blue);
            sensorColor = NormalizeColor(sensorColor);

            // Calculate distance
            double threshold = 100;
            double dist;
            double detect_dist = 0;
            int match = 2;
            for( int i = 0; i < matches.length; i++) {
                dist = Math.sqrt(Math.pow(matches[i].red - sensorColor.red, 2) +
                        Math.pow(matches[i].green - sensorColor.green, 2) +
                        Math.pow(matches[i].blue - sensorColor.blue, 2));
                if(dist < threshold){
                    match = i;
                    detect_dist = dist;
                }
            }


            telemetry.addData("Red Match Red: ", red_match.red);
            telemetry.addData("Red Match Green: ", red_match.green);
            telemetry.addData("Red Match Blue: ", red_match.blue);



            telemetry.addData("Red: ", sensorColor.red);
            telemetry.addData("Green: ", sensorColor.green);
            telemetry.addData("Blue: ", sensorColor.blue);
            telemetry.addData("Dist: ", detect_dist);
            telemetry.addData("Match Color: ", colors[match]);

            telemetry.update();
        }
    }

    private Color NormalizeColor(Color originalColor){
        Color returnColor = new Color(0,0,0);
        double totalColor = originalColor.red + originalColor.green + originalColor.blue;
        returnColor.red = (int) ((originalColor.red / totalColor) * 1000);
        returnColor.green = (int) ((originalColor.green / totalColor) * 1000);
        returnColor.blue = (int) ((originalColor.blue / totalColor) * 1000);
        return returnColor;
    }
}
