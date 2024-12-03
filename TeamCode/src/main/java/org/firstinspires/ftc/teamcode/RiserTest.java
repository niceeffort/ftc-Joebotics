package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name="RiserTest")
public class RiserTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Riser myRiser = new Riser(hardwareMap);

       /* DcMotorEx riserMotor = hardwareMap.get(DcMotorEx.class, "riser");
        OverflowEncoder riserEncoder = new OverflowEncoder(new RawEncoder(riserMotor));
        riserEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
        riserMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        riserMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        riserMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        riserMotor.setTargetPosition(0);
        riserMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);*/

        waitForStart();

        //riserMotor.setPower(0.5);
        //riserMotor.setTargetPosition(1900);
        //sleep(2000);

        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.HIGH_BAR));
        telemetry.addLine("Up position");
        telemetry.update();
        sleep(2000);
        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.DOWN));
        telemetry.addLine("Down position");
        telemetry.update();

    }
}
