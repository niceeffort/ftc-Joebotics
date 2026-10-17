package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "WheelTest")
public class WheelTest extends LinearOpMode {

    private final SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override
    public void runOpMode() {

        robot.initialize(true);

        DcMotor frontRight = hardwareMap.get(
                DcMotor.class, "front_right_motor");

        frontRight.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        frontRight.setPower(0);

        telemetry.addLine("Ready to test");
        telemetry.update();

        waitForStart();


        robot.resetHeading();  // Reset heading to set a baseline for Auto

        if (opModeIsActive()) {
            robot.drive(10, 0.30, .25);

            sleep(1000);

            frontRight.setPower(0);

            telemetry.addData("Commanded power",
                    frontRight.getPower());
            telemetry.addLine("Stop command sent");
            telemetry.update();

            sleep(3000);
        }

        frontRight.setPower(0);
    }
}