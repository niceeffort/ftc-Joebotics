package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

public class BasicFunctionsExample extends LinearOpMode{
    @Override
    public void runOpMode()throws InterruptedException{
        DcMotor press = hardwareMap.dcMotor.get("press");

        waitForStart();
        while (opModeIsActive()){
            boolean a = gamepad1.a;

            if(a = true){
                press.setPower(.5);
            } else if (a = false){
                press.setPower(0);
            }
        }
    }
}
