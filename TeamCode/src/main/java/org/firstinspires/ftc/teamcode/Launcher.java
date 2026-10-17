
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {
    private final DcMotor launcherMotor;

    public Launcher(HardwareMap hardwareMap) {
        launcherMotor = hardwareMap.get(DcMotor.class, "launcher");
    }

    public void setPower(double power) {
        launcherMotor.setPower(power);
    }

}
