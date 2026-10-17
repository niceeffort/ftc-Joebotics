
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Pusher {
    private final CRServo pusherCRServo;

    public Pusher(HardwareMap hardwareMap) {
        pusherCRServo = hardwareMap.get(CRServo.class, "pusher");
    }

    public void setPower(double power) {
        pusherCRServo.setPower(power);
    }

}
