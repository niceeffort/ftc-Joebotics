package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RRClawTest")
public class RRClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //Claw
        Claw myClaw = new Claw(hardwareMap);
        //Riser
        Riser myRiser = new Riser(hardwareMap);

        waitForStart();



        //Claw
        telemetry.addLine("Starting open");
        telemetry.update();
        Actions.runBlocking(myClaw.setPosition(Claw.ClawPosition.OPEN));
        telemetry.addLine("Ending open");
        telemetry.update();
        sleep(2000);
        telemetry.addLine("Starting close");
        telemetry.update();
        Actions.runBlocking(myClaw.setPosition(Claw.ClawPosition.CLOSE));
        sleep(2000);
        telemetry.addLine("Ending close");
        telemetry.update();

        //Riser
        telemetry.addLine("Starting up");
        telemetry.update();
        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.TOP));
        telemetry.addLine("Ending up");
        telemetry.update();
        sleep(2000);
        telemetry.addLine("Starting down");
        telemetry.update();
        Actions.runBlocking(myRiser.setPosition(Riser.RiserPosition.BOTTOM));
        sleep(2000);
        telemetry.addLine("Ending down");
        telemetry.update();
    }
}