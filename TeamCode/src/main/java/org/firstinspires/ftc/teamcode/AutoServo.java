package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name ="AutoServo")
public class AutoServo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo claw = hardwareMap.servo.get("claw");
        waitForStart();
        double position = 0.0;
        while(opModeIsActive()){
            claw.setPosition(position);
            sleep(1000);
            if(position < 1.0){
                position = 1.0;
            }else {
                position = 0.0;
            }
        }
    }
}
