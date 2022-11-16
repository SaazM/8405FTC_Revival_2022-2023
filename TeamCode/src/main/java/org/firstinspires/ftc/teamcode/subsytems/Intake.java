package org.firstinspires.ftc.teamcode.subsytems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public Servo claw;

    public Intake(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "intake");
        claw.resetDeviceConfigurationForOpMode();
    }

    public void open() {
        claw.setPosition(0);
    }

    public void close() {
        claw.setPosition(0.6);
    }
}
