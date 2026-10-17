
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class SimplifiedOdometryRobot {

    // Calibrate this for your motor, gearing, and wheel size.
    private static final double COUNTS_PER_INCH = 45.28;
    private static final double MAX_POWER = 0.6;

    private DcMotor leftFrontDrive;
    private DcMotor rightFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightBackDrive;

    private IMU imu;
    private final LinearOpMode myOpMode;

    public SimplifiedOdometryRobot(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    // Initialize drive motors and IMU.
    public void initialize(boolean showTelemetry) {

        leftFrontDrive = setupDriveMotor(
                "front_left_motor", DcMotor.Direction.REVERSE);

        rightFrontDrive = setupDriveMotor(
                "front_right_motor", DcMotor.Direction.FORWARD);

        leftBackDrive = setupDriveMotor(
                "back_left_motor", DcMotor.Direction.REVERSE);

        rightBackDrive = setupDriveMotor(
                "back_right_motor", DcMotor.Direction.REVERSE);

        imu = myOpMode.hardwareMap.get(IMU.class, "imu");

        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                )
        ));

        imu.resetYaw();

        if (showTelemetry) {
            myOpMode.telemetry.addData("Robot", "Initialized");
            myOpMode.telemetry.addData("IMU", "Ready");
            myOpMode.telemetry.update();
        }
    }

    private DcMotor setupDriveMotor(
            String deviceName, DcMotor.Direction direction) {

        DcMotor motor = myOpMode.hardwareMap.get(
                DcMotor.class, deviceName);

        motor.setDirection(direction);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return motor;
    }

    public void resetHeading() {
        imu.resetYaw();
    }

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

        // Stop before starting a new movement.
        stopRobot();

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

        try {
            // Continue until every motor reaches its target,
            // the timeout expires, or the OpMode is stopped.
            while (myOpMode.opModeIsActive()
                    && timer.seconds() < timeoutSeconds
                    && (leftFrontDrive.isBusy()
                    || rightFrontDrive.isBusy()
                    || leftBackDrive.isBusy()
                    || rightBackDrive.isBusy())) {

                myOpMode.telemetry.addData(
                        "Front Left", "%d / %d",
                        leftFrontDrive.getCurrentPosition(),
                        lfTarget);

                myOpMode.telemetry.addData(
                        "Front Right", "%d / %d",
                        rightFrontDrive.getCurrentPosition(),
                        rfTarget);

                myOpMode.telemetry.addData(
                        "Back Left", "%d / %d",
                        leftBackDrive.getCurrentPosition(),
                        lbTarget);

                myOpMode.telemetry.addData(
                        "Back Right", "%d / %d",
                        rightBackDrive.getCurrentPosition(),
                        rbTarget);

                myOpMode.telemetry.addData(
                        "Drive time", "%.1f / %.1f",
                        timer.seconds(), timeoutSeconds);

                myOpMode.telemetry.update();
                myOpMode.idle();
            }
        } finally {
            // Always remove power from all four drive motors.
            stopRobot();

            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        // Optional pause after the movement has stopped.
        if (myOpMode.opModeIsActive() && holdTime > 0) {
            myOpMode.sleep((long) (holdTime * 1000));
        }

        stopRobot();
    }

    public void stopRobot() {
        if (leftFrontDrive != null) {
            leftFrontDrive.setPower(0);
        }
        if (rightFrontDrive != null) {
            rightFrontDrive.setPower(0);
        }
        if (leftBackDrive != null) {
            leftBackDrive.setPower(0);
        }
        if (rightBackDrive != null) {
            rightBackDrive.setPower(0);
        }
    }

    // Positive heading = counterclockwise.
    // Negative heading = clockwise.
    public void turnTo(double targetHeading) {

        ElapsedTime timer = new ElapsedTime();
        double error = getHeadingError(targetHeading);

        while (myOpMode.opModeIsActive()
                && Math.abs(error) > 2.0
                && timer.seconds() < 5.0) {

            double turnPower = Range.clip(
                    Math.abs(error) * 0.01, 0.12, 0.45);

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

            myOpMode.telemetry.addData("Heading", getHeading());
            myOpMode.telemetry.addData("Target", targetHeading);
            myOpMode.telemetry.addData("Error", error);
            myOpMode.telemetry.update();

            myOpMode.idle();
            error = getHeadingError(targetHeading);
        }

        stopRobot();
    }

    private double getHeading() {
        return imu.getRobotYawPitchRollAngles()
                .getYaw(AngleUnit.DEGREES);
    }

    private double getHeadingError(double targetHeading) {
        double error = targetHeading - getHeading();

        while (error > 180) {
            error -= 360;
        }

        while (error < -180) {
            error += 360;
        }

        return error;
    }
}
