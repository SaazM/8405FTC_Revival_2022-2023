package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.apriltags.aprilTagsInit;


public class AprilTagsCompTest extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        aprilTagsInit init = new aprilTagsInit(hardwareMap,telemetry);
        init.initialize();

        while (!isStarted() && !isStopRequested())
        {
            init.search();
            sleep(20);
        }
        waitForStart();
        int finalID = init.stopAndSave();
        telemetry.addLine(Integer.toString(finalID));
        telemetry.update();
    }
}
