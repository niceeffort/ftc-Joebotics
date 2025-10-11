package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name="Moto Control")
public class DcMotor extends LinearOpMode {

    DcMotorEx motor;
    static final double COUNTS_PER_MOTOR_REV = 28.0;
    static final double DRIVE_GEAR_REDUCTION = 30.24;
    static final double WHEEL_CIRCUMFERENCE = 58 * 3.14;

    static final double COUNTS_PER_WHEEL_REV = COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION;
    static final double COUNTS_PER_MM = COUNTS_PER_WHEEL_REV / WHEEL_CIRCUMFERENCE;

    @Override
    public void runOpMode() throws InterruptedException{
        //initialize motor
        motor = hardwareMap.get(DcMotorEx.class,"motor");

        //setting direction
        motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //setting run mode
        //disables default velocity control
        motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        int target = (int)(610 * COUNTS_PER_MM);
        double TPS = (175/60) * COUNTS_PER_WHEEL_REV;

        waitForStart();

        while (opModeIsActive()) {
            motor.setVelocity(TPS);
        }
    }
}
