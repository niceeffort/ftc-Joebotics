
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class SimplifiedOdometryRobot {

    // IMPORTANT: This is a placeholder.
    // Calibrate this value for your motors, gearing, and wheels.
    private static final double COUNTS_PER_INCH = 45.28;

    private static final double MAX_POWER = 0.6;

    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;

    private IMU imu;
    private LinearOpMode myOpMode;

    public SimplifiedOdometryRobot(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    // Initialize the four drive motors.
    public void initialize(boolean showTelemetry) {

        leftFrontDrive = setupDriveMotor("front_left_motor", DcMotor.Direction.REVERSE);
        rightFrontDrive = setupDriveMotor("front_right_motor", DcMotor.Direction.FORWARD);
        leftBackDrive = setupDriveMotor("back_left_motor", DcMotor.Direction.REVERSE);
        rightBackDrive = setupDriveMotor("back_right_motor", DcMotor.Direction.FORWARD);

        imu = myOpMode.hardwareMap.get(IMU.class, "imu");

        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        ));

        imu.resetYaw();
    }

    public void resetHeading() {
        imu.resetYaw();
    }

    private DcMotor setupDriveMotor(
            String deviceName, DcMotor.Direction direction) {

        DcMotor motor = myOpMode.hardwareMap.get(
                DcMotor.class, deviceName);

        motor.setDirection(direction);
        motor.setZeroPowerBehavior(
                DcMotor.ZeroPowerBehavior.BRAKE);

        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return motor;
    }

    // Drive forward or backward a specified distance in inches.
    // Positive distance = forward.
    // Negative distance = backward.
    public void drive(double distanceInches,
                      double power,
                      double holdTime) {

        int counts = (int) (distanceInches * COUNTS_PER_INCH);

        moveToTargets(
                leftFrontDrive.getCurrentPosition() + counts,
                rightFrontDrive.getCurrentPosition() + counts,
                leftBackDrive.getCurrentPosition() + counts,
                rightBackDrive.getCurrentPosition() + counts,
                power,
                Math.max(3.0, Math.abs(distanceInches) / 2.0 + 3.0),
                holdTime
        );
    }

    // Strafe left or right.
    // Positive distance = left.
    // Negative distance = right.
    public void strafe(double distanceInches,
                       double power,
                       double holdTime) {

        int counts = (int) (distanceInches * COUNTS_PER_INCH);

        moveToTargets(
                leftFrontDrive.getCurrentPosition() - counts,
                rightFrontDrive.getCurrentPosition() + counts,
                leftBackDrive.getCurrentPosition() + counts,
                rightBackDrive.getCurrentPosition() - counts,
                power,
                Math.max(3.0, Math.abs(distanceInches) / 2.0 + 3.0),
                holdTime
        );
    }

    private void moveToTargets(
            int lfTarget,
            int rfTarget,
            int lbTarget,
            int rbTarget,
            double power,
            double timeoutSeconds,
            double holdTime) {

        leftFrontDrive.setTargetPosition(lfTarget);
        rightFrontDrive.setTargetPosition(rfTarget);
        leftBackDrive.setTargetPosition(lbTarget);
        rightBackDrive.setTargetPosition(rbTarget);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double safePower = Range.clip(
                Math.abs(power), 0.0, MAX_POWER);

        leftFrontDrive.setPower(safePower);
        rightFrontDrive.setPower(safePower);
        leftBackDrive.setPower(safePower);
        rightBackDrive.setPower(safePower);

        ElapsedTime timer = new ElapsedTime();

        while (myOpMode.opModeIsActive()
                && timer.seconds() < timeoutSeconds
                && (leftFrontDrive.isBusy()
                || rightFrontDrive.isBusy()
                || leftBackDrive.isBusy()
                || rightBackDrive.isBusy())) {

            myOpMode.idle();
        }

        // Stop if the targets are reached or the timeout expires.
        stopRobot();

        // Hold briefly at the target, if requested.
        // Motors remain in RUN_TO_POSITION during this interval.
        if (myOpMode.opModeIsActive()
                && holdTime > 0
                && timer.seconds() < timeoutSeconds) {

            ElapsedTime holdTimer = new ElapsedTime();

            while (myOpMode.opModeIsActive()
                    && holdTimer.seconds() < holdTime) {
                myOpMode.idle();
            }
        }

        stopRobot();

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void stopRobot() {
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
    // Turn to a target heading in degrees.
// Positive heading = counterclockwise.
// Negative heading = clockwise.
    public void turnTo(double targetHeading) {

        ElapsedTime timer = new ElapsedTime();

        double error = getHeadingError(targetHeading);

        // Stop when within 2 degrees, the OpMode stops,
        // or 5 seconds have passed.
        while (myOpMode.opModeIsActive()
                && Math.abs(error) > 2.0
                && timer.seconds() < 5.0) {

            // Adjust turning power based on the remaining angle.
            double turnPower = Range.clip(
                    Math.abs(error) * 0.01,
                    0.12,
                    0.45
            );

            // Turn in the direction of the target.
            if (error > 0) {
                leftFrontDrive.setPower(-turnPower);
                leftBackDrive.setPower(-turnPower);
                rightFrontDrive.setPower(turnPower);
                rightBackDrive.setPower(turnPower);
            } else {
                leftFrontDrive.setPower(turnPower);
                leftBackDrive.setPower(turnPower);
                rightFrontDrive.setPower(-turnPower);
                rightBackDrive.setPower(-turnPower);
            }

            myOpMode.telemetry.addData(
                    "Heading", imu.getRobotYawPitchRollAngles()
                            .getYaw(AngleUnit.DEGREES));
            myOpMode.telemetry.addData("Target", targetHeading);
            myOpMode.telemetry.addData("Error", error);
            myOpMode.telemetry.update();

            myOpMode.idle();

            error = getHeadingError(targetHeading);
        }

        stopRobot();
    }

    // Calculate the shortest angular difference to the target.
    private double getHeadingError(double targetHeading) {
        double currentHeading = imu.getRobotYawPitchRollAngles()
                .getYaw(AngleUnit.DEGREES);

        double error = targetHeading - currentHeading;

        while (error > 180) {
            error -= 360;
        }

        while (error < -180) {
            error += 360;
        }

        return error;
    }
}
