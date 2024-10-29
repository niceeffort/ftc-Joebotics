package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "WhiteMecanumDriveTest")
public class WhiteMecanumDriveTest extends LinearOpMode {
    WhiteMecanumDrive myDrive = null;
    @Override
    public void runOpMode() throws InterruptedException {
        myDrive = new WhiteMecanumDrive(this);
        waitForStart();
        while(opModeIsActive()){
            myDrive.Update(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_trigger, gamepad1.right_trigger);
        }
    }
}