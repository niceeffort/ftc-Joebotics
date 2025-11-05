package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "DecodeWhiteDrive")
public class DecodeWhiteDrive extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
        boolean fieldCentric = false;
        double powerFactor = 0.75;
        boolean lowPowerMode = false;
        boolean aButtonPress = false;

        // Get the motors
        DcMotor bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        DcMotor ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        DcMotor ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        DcMotor bk_rt = hardwareMap.dcMotor.get("back_right_motor");
        DcMotor lt_o = hardwareMap.dcMotor.get("left-outtake");
        DcMotor rt_o = hardwareMap.dcMotor.get("right_outtake");
        //TODO: Ramp needs to not stop
        DcMotor ramp = hardwareMap.dcMotor.get("ramp");
        CRServo lt_i = hardwareMap.crservo.get("left_intake");
        CRServo rt_i = hardwareMap.crservo.get("right_intake");

        // This part may be robot dependant
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);
        lt_i.setDirection(DcMotorSimple.Direction.REVERSE);

        // The IMU will be used for field centric driving
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.FORWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive()) {
            double left_stick_x = gamepad1.left_stick_x;
            double left_stick_y = -gamepad1.left_stick_y;
            double triggers = gamepad1.left_trigger - gamepad1.right_trigger;
            double outtakePower = gamepad2.left_trigger;
            double intakePower = gamepad2.right_trigger;

            //Motor power!
            if (Math.abs(triggers) < 0.05) triggers = 0;
            double bk_lt_power = -left_stick_x + left_stick_y - triggers;
            double ft_lt_power = left_stick_x + left_stick_y - triggers;
            double ft_rt_power = -left_stick_x + left_stick_y + triggers;
            double bk_rt_power = left_stick_x + left_stick_y + triggers;

            //Outtake code
            lt_o.setPower(outtakePower);
            rt_o.setPower(outtakePower);

            //Intake code
            lt_i.setPower(intakePower);
            rt_i.setPower(intakePower);

            //double botHeading = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the robot's rotation
            if(fieldCentric) {

                // This can be used if the robot become uncalibrated. Point the robot away from you and press start.
                if (gamepad1.start) {
                    imu.resetYaw();
                }
                double rotX = left_stick_x * Math.cos(-botHeading) - left_stick_y * Math.sin(-botHeading);
                double rotY = left_stick_x * Math.sin(-botHeading) + left_stick_y * Math.cos(-botHeading);
                bk_lt_power = -rotX + rotY - triggers;
                ft_lt_power = rotX + rotY - triggers;
                ft_rt_power = -rotX + rotY + triggers;
                bk_rt_power = rotX + rotY + triggers;
            }

            // Normalize the power
            double max = Math.max(1.0, Math.abs(bk_lt_power));
            max = Math.max(max, Math.abs(ft_lt_power));
            max = Math.max(max, Math.abs(ft_rt_power));
            max = Math.max(max, Math.abs(bk_rt_power));
            bk_lt_power /= max;
            ft_lt_power /= max;
            ft_rt_power /= max;
            bk_rt_power /= max;

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
            bk_lt.setPower(bk_lt_power * powerFactor);
            ft_lt.setPower(ft_lt_power * powerFactor);
            ft_rt.setPower(ft_rt_power * powerFactor);
            bk_rt.setPower(bk_rt_power * powerFactor);
        }
        bk_lt.setPower(0);
        ft_lt.setPower(0);
        ft_rt.setPower(0);
        bk_rt.setPower(0);
    }
}


