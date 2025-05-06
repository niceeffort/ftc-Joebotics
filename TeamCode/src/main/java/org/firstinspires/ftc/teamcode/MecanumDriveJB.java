package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MecanumDriveJB {
    boolean fieldCentric = true;
    LinearOpMode myOpMode;
    HardwareMap hardwareMap;
    Gamepad gamepad1;
    DcMotor bk_lt;
    DcMotor ft_lt;
    DcMotor ft_rt;
    DcMotor bk_rt;
    IMU imu;
    Telemetry telemetry;

    public MecanumDriveJB(LinearOpMode opMode){
        myOpMode = opMode;
        hardwareMap = myOpMode.hardwareMap;
        gamepad1 = myOpMode.gamepad1;
        telemetry = myOpMode.telemetry;
        Initialize();
    }

    private void Initialize(){
        // Get the motors
        bk_lt = hardwareMap.dcMotor.get("back_left_motor");
        ft_lt = hardwareMap.dcMotor.get("front_left_motor");
        ft_rt = hardwareMap.dcMotor.get("front_right_motor");
        bk_rt = hardwareMap.dcMotor.get("back_right_motor");

        // These may be robot dependant
        bk_lt.setDirection(DcMotor.Direction.REVERSE);
        ft_lt.setDirection(DcMotor.Direction.REVERSE);

        // The IMU will be used to capture the heading for field centric driving
        imu = hardwareMap.get(IMU.class, "imu");

        // Set this to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

    }

    public void Update(double input_left_stick_x, double input_left_stick_y, double input_left_trigger, double input_right_trigger){
        double left_stick_x = input_left_stick_x;
        double left_stick_y = -input_left_stick_y;
        double triggers = input_left_trigger - input_right_trigger;

        if (Math.abs(triggers) < 0.05) triggers = 0;
        double bk_lt_power = -left_stick_x + left_stick_y - triggers;
        double ft_lt_power = left_stick_x + left_stick_y - triggers;
        double ft_rt_power = -left_stick_x + left_stick_y + triggers;
        double bk_rt_power = left_stick_x + left_stick_y + triggers;

        // I have seen two ways to get this information. The latter looks less error prone
        //double botHeading = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
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

        bk_lt.setPower(bk_lt_power);
        ft_lt.setPower(ft_lt_power);
        ft_rt.setPower(ft_rt_power);
        bk_rt.setPower(bk_rt_power);

        telemetry.addData("Heading (degrees)", " %.1f", botHeading * 180.0 / Math.PI);
        telemetry.addData("Left Stick X", left_stick_x);
        telemetry.addData("Left Stick Y", left_stick_y);
        telemetry.addData("Triggers", triggers);
        telemetry.update();
    }
}
