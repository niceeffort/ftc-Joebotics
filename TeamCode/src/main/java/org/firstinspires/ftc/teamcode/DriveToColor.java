package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "DriveToColor")
public class DriveToColor extends LinearOpMode {
    MecanumDrive myDrive = null;
    ColorSensorJB colorSensor;
    boolean color_found = false;
    @Override
    public void runOpMode() throws InterruptedException {
        myDrive = new MecanumDrive(this);
        colorSensor = new ColorSensorJB(this);

        waitForStart();
        while(opModeIsActive()){
            int color = colorSensor.Update();

            if(color > 0){
                color_found = true;
            }
            else{
                color_found = false;
            }
            if(color_found){
                myDrive.Update(0,0,0,0);
            }
            else{
                myDrive.Update(0, .25, 0, 0);
            }
        }
    }
}
