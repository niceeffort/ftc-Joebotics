package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ServoMotorTest")
public class ServoMotorTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor ft_pr = hardwareMap.dcMotor.get("front_pitcher");
        DcMotor bk_pr = hardwareMap.dcMotor.get("back_pitcher");
        CRServo cam = hardwareMap.crservo.get("cam");

        waitForStart();
        while (opModeIsActive()) {

            boolean pitcherGo = gamepad2.y;
            boolean pitcherStop = gamepad2.a;
            boolean camGo = gamepad2.x;
            boolean camStop = gamepad2.b;

            ft_pr.setDirection(DcMotor.Direction.REVERSE);
            bk_pr.setDirection(DcMotor.Direction.REVERSE);

            ft_pr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            bk_pr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            //Pitcher code
            if (pitcherGo) {
                ft_pr.setPower(.5);
                bk_pr.setPower(.5);
            } else if (pitcherStop) {
                ft_pr.setPower(0.0);
                bk_pr.setPower(0.0);            }

            //Cam code
            if (camGo) {
                cam.setPower(.25);
            } else if (camStop) {
                cam.setPower(0);
            }
        }
    }
}
