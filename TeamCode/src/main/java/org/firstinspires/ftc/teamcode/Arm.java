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

     public enum ArmPos {HIGH_BAR, BOTTOM}

     private final int[] armPos = new int[]{1900, 0};

     private final int tolerance = 100;

     public Arm(HardwareMap hardwareMap) {
         armMotor = hardwareMap.get(DcMotorEx.class, "arm");
         armEncoder = new OverflowEncoder(new RawEncoder(armMotor));
         armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
         armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
         armMotor.setTargetPosition(armPos[ArmPos.BOTTOM.ordinal()]);
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
