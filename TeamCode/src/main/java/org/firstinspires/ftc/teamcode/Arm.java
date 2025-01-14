package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

 class Arm {
     private final DcMotorEx armMotor;
     private final OverflowEncoder armEncoder;

     final double ARM_TICKS_PER_DEGREE =
             28
                     * 250047.0 / 4913.0
                     * 100.0 / 20.0
                     * 1 / 360.0;

     public enum ArmPos {ARM_COLLECT, ARM_SCORE, DOWN}

     private final int[] armPos = new int[]{(int) (250 * ARM_TICKS_PER_DEGREE), (int) (160 * ARM_TICKS_PER_DEGREE), 0};

     private final int tolerance = 100;

     public Arm(HardwareMap hardwareMap) {
         armMotor = hardwareMap.get(DcMotorEx.class, "arm");
         armEncoder = new OverflowEncoder(new RawEncoder(armMotor));
         armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         armMotor.setTargetPosition(armPos[ArmPos.DOWN.ordinal()]);
         armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
         armMotor.setPower(0.5);
     }

     public Action setPosition(ArmPos position) {
         return new Action() {
             private boolean initialized = false;
             private final int minPos = armPos[position.ordinal()] - tolerance;
             private  final int maxPos = armPos[position.ordinal()] + tolerance;

             @Override
             public boolean run(@NonNull TelemetryPacket packet) {
                 if(!initialized) {
                     armMotor.setTargetPosition(armPos[position.ordinal()]);
                     initialized = true;
                 }

                 double motorPosition = armMotor.getCurrentPosition();
                 packet.put("armPosition", motorPosition);

                 return !(motorPosition < maxPos && motorPosition > minPos);
             }
         };
     }
}
