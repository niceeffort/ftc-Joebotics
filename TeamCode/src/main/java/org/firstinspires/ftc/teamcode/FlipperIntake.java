package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "FlipperIntake")
public class FlipperIntake extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        boolean fieldCentric = false;
        double powerFactor = 0.75;
        boolean lowPowerMode = false;
        boolean aButtonPress = false;

        // Get the motors
        CRServo intake_one = hardwareMap.crservo.get("intake_one");
        CRServo intake_two = hardwareMap.crservo.get("intake_two");
        CRServo intake_three = hardwareMap.crservo.get("intake_three");

        intake_one.setDirection(CRServo.Direction.REVERSE);
        intake_two.setDirection(CRServo.Direction.REVERSE);
        intake_three.setDirection(CRServo.Direction.REVERSE);


        // This part may be robot dependant

        // The IMU will be used for field centric driving
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive()) {
            boolean intakeGo = gamepad2.y;
            boolean intakeStop = gamepad2.a;

            //Motor power!

            //Intake code
            if (intakeGo) {
                intake_one.setPower(.5);
                intake_two.setPower(.5);
                intake_three.setPower(.5);
            } else if (intakeStop) {
                intake_one.setPower(0);
                intake_two.setPower(0);
                intake_three.setPower(0);
            }


            //double botHeading = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the robot's rotation
            if(fieldCentric) {

                // This can be used if the robot become uncalibrated. Point the robot away from you and press start.
                if (gamepad1.start) {
                    imu.resetYaw();
                }

            }

            // Normalize the power

            if (gamepad1.a && !aButtonPress) {
                aButtonPress = true;
                if (lowPowerMode){
                    powerFactor = 1.0;
                    lowPowerMode = false;
                } else{
                    powerFactor = 0.25;
                    lowPowerMode = true;
                }

            } else if (!gamepad1.a && aButtonPress) {
                aButtonPress = false;
            }
        }
    }

}

