package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

import java.util.Locale;

@TeleOp(name="Pinpoint Test", group="Linear Opmode")
public class PinPointTest extends LinearOpMode {
    GoBildaPinpointDriver pinPoint;

    @Override
    public void runOpMode() throws InterruptedException {
        pinPoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinPoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinPoint.setOffsets(-84.0, -168.0, DistanceUnit.MM); //these are tuned for 3110-0002-0001 Product Insight #1
        pinPoint.resetPosAndIMU();
        waitForStart();
        while (opModeIsActive()) {
            pinPoint.update();
            Pose2D pos = pinPoint.getPosition();
            telemetry.addLine("Pressing X will reset the encoder and IMU");
            telemetry.addLine("Pressing B will reverse the encoder directions");
            telemetry.addLine("Pressing A will set the encoder directions to forward");
            telemetry.addData("X", pos.getX(DistanceUnit.INCH));
            telemetry.addData("Y", pos.getY(DistanceUnit.INCH));
            telemetry.addData("Old Heading", pos.getHeading(AngleUnit.DEGREES));
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
