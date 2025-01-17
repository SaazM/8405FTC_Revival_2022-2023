package org.firstinspires.ftc.teamcode.roadrunnerfiles.opmode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Robot;

import java.util.Objects;

/**
 * This routine is designed to calculate the maximum angular velocity your bot can achieve under load.
 * <p>
 * Upon pressing start, your bot will turn at max power for RUNTIME seconds.
 * <p>
 * Further fine tuning of MAX_ANG_VEL may be desired.
 */
@Disabled
@Config
@Autonomous(group = "drive")
public class MaxAngularVeloTuner extends LinearOpMode {
    public static double RUNTIME = 4.0;

    private ElapsedTime timer;
    private double maxAngVelocity = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap, gamepad1);

        robot.drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addLine("Your bot will turn at full speed for " + RUNTIME + " seconds.");
        telemetry.addLine("Please ensure you have enough space cleared.");
        telemetry.addLine("");
        telemetry.addLine("Press start when ready.");
        telemetry.update();

        waitForStart();

        telemetry.clearAll();
        telemetry.update();

        robot.drive.setDrivePower(new Pose2d(0, 0, 1));
        timer = new ElapsedTime();

        while (!isStopRequested() && timer.seconds() < RUNTIME) {
            robot.drive.updatePoseEstimate();

            Pose2d poseVelo = Objects.requireNonNull(robot.drive.getPoseVelocity(), "poseVelocity() must not be null. Ensure that the getWheelVelocities() method has been overridden in your localizer.");

            maxAngVelocity = Math.max(poseVelo.getHeading(), maxAngVelocity);
        }

        robot.drive.setDrivePower(new Pose2d());

        telemetry.addData("Max Angular Velocity (rad)", maxAngVelocity);
        telemetry.addData("Max Angular Velocity (deg)", Math.toDegrees(maxAngVelocity));
        telemetry.update();

        while (!isStopRequested()) idle();
    }
}