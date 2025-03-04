package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Pinpoint Test", group="Linear Opmode")
public class PinPointTest extends LinearOpMode {
    GoBildaPinpointDriver pinPoint;

    @Override
    public void runOpMode() throws InterruptedException {
        pinPoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        waitForStart();
        while (opModeIsActive()) {
            pinPoint.update();
            telemetry.addLine("Pressing X will reset the encoder and IMU");
            telemetry.addLine("Pressing B will reverse the encoder directions");
            telemetry.addLine("Pressing A will set the encoder directions to forward");
            telemetry.addData("X", pinPoint.getPosX());
            telemetry.addData("Y", pinPoint.getPosY());
            telemetry.addData("Heading", pinPoint.getHeading());
            telemetry.update();

            if (gamepad1.x) {
                pinPoint.resetPosAndIMU();
            }
            if (gamepad1.b) {
                pinPoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED);
            }
            if (gamepad1.a) {
                pinPoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
            }
        }
    }
}
