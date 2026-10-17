
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

@TeleOp(name = "PusherTest")
public class PusherTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        CRServo pusher =
                hardwareMap.get(CRServo.class, "pusher");

        telemetry.addData("Status", "Ready");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Pusher", "Testing forward");
            telemetry.update();

            pusher.setPower(0.3);
            sleep(2000);

            pusher.setPower(0.0);

            telemetry.addData("Pusher", "Test finished");
            telemetry.update();
        }

        pusher.setPower(0.0);
    }
}
