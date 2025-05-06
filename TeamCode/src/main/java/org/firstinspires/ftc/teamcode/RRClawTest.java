package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "RRClawTest")
public class RRClawTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        //Claw
        Claw myClaw = new Claw(hardwareMap);
        //Lift
        Lift myLift = new Lift(hardwareMap);

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

        //Lift
        telemetry.addLine("Starting up");
        telemetry.update();
        Actions.runBlocking(myLift.setPosition(Lift.LiftPosition.TOP));
        telemetry.addLine("Ending up");
        telemetry.update();
        sleep(2000);
        telemetry.addLine("Starting down");
        telemetry.update();
        Actions.runBlocking(myLift.setPosition(Lift.LiftPosition.BOTTOM));
        sleep(2000);
        telemetry.addLine("Ending down");
        telemetry.update();
    }
}