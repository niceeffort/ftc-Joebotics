package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name ="DriveBotAuto")
public class DriveBotAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Servo servo = hardwareMap.servo.get("test_servo");
        waitForStart();
        double position = 0.0;
        while(opModeIsActive()){
            servo.setPosition(position);
            sleep(1000);
            if(position < 1.0){
                position = 1.0;
            }else {
                position = 0.0;
            }
        }
    }
}
