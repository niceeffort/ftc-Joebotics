package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ColorSensorTest")
public class ColorSensorTest extends LinearOpMode{
    ColorSensorJB colorSensor;
    @Override
    public void runOpMode() throws InterruptedException {
        colorSensor = new ColorSensorJB(this);

        waitForStart();
        while (opModeIsActive()) {
            int color = colorSensor.Update();
            telemetry.addData("color", color);
        }
    }
}
