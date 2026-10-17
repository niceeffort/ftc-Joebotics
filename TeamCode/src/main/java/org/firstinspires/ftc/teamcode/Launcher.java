package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Launcher {

    private final DcMotor launcher;

    // Enumeration for launcher position
    public enum LauncherPosition {GO, STOP}
    private final int[] launcherPositions = new int[] {75, 0};

    public Launcher(HardwareMap hardwareMap){

        launcher = hardwareMap.get(DcMotor.class, "launcher");
        launcher.setDirection(DcMotor.Direction.REVERSE);
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launcher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        launcher.setTargetPosition(launcherPositions[LauncherPosition.GO.ordinal()]);
        launcher.setTargetPosition(launcherPositions[LauncherPosition.STOP.ordinal()]);
        launcher.setPower(1.0);
    }

    public Action setPosition(Launcher.LauncherPosition position) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    initialized = true;
                    launcher.setTargetPosition(launcherPositions[position.ordinal()]);
                }

                int currentPosition = launcher.getCurrentPosition();
                packet.put("launcher Position", currentPosition);
                packet.put("launcher Target Position", launcher.getTargetPosition());
                return !(currentPosition == launcherPositions[position.ordinal()]);
            }
        };
    }
}