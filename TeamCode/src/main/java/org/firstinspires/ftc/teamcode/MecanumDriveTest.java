package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MecanumDriveTest")
public class MecanumDriveTest extends LinearOpMode {
    MecanumDrive myDrive = null;
    @Override
    public void runOpMode() throws InterruptedException {
        myDrive = new MecanumDrive(this);
        waitForStart();
        while(opModeIsActive()){
            myDrive.Update();
        }
    }
}
