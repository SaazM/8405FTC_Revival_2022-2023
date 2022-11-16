package org.firstinspires.ftc.teamcode;

import java.lang.*;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class Auton {
    private boolean direction;
    private int parkingZone;
    public Auton(boolean left, int tag_id) {
        this.direction = left;
        tag_id++;
        if (tag_id == 2) {
            parkingZone = 1;
        } else if (tag_id == 1) {
            parkingZone = 2;
        } else if (tag_id == 3) {
            parkingZone = 3;
        }
    }
    public void init(Robot drive) {

    }

    public void loop(Robot drive, HardwareMap hardwareMap) {
        Trajectory trajSeq1 = drive.trajectoryBuilder(new Pose2d())
            .forward(30)
            .build();
        drive.followTrajectory(trajSeq1);
//        init(drive);
    }


    public void runAutonAsync(Robot drive, HardwareMap hardwareMap) {
        loop(drive, hardwareMap);
        DcMotorEx liftLeft = hardwareMap.get(DcMotorEx.class, "leftLift");
        DcMotorEx liftRight = hardwareMap.get(DcMotorEx.class, "rightLift");
        goToMediumGoal(liftLeft, liftRight);

        drive.update();
    }

    public void runAutonParkOnly(Robot drive, HardwareMap hardwareMap) {
        Intake intake = new Intake(hardwareMap);

        drive.setMotorPowers(0.1,0.1,0.1,0.1);
        intake.close();
        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(new Pose2d())
            .forward(25)
            .build();
        drive.followTrajectorySequence(trajSeq);



        if (parkingZone == 1) { // red
            Trajectory park = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(36)
                .build();
            drive.followTrajectory(park);
        } else if (parkingZone == 3) { // blue
            Trajectory park = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(36)
                .build();
            drive.followTrajectory(park);
        }

    }

    public void runAutonLeft(Robot drive, HardwareMap hardwareMap) throws InterruptedException {
        DcMotorEx liftLeft = hardwareMap.get(DcMotorEx.class, "leftLift");
        DcMotorEx liftRight = hardwareMap.get(DcMotorEx.class, "rightLift");
        drive.setMotorPowers(0, 0, 0, 0);
        Intake intake = new Intake(hardwareMap);
        intake.close();
        //medium goal
        TrajectorySequence trajSeq1 = drive.trajectorySequenceBuilder(new Pose2d())
            .strafeLeft(49)
            .build();
        drive.followTrajectorySequence(trajSeq1);
        goToMediumGoal(liftLeft, liftRight);
        TrajectorySequence trajSeq2 = drive.trajectorySequenceBuilder(trajSeq1.end())
            .forward(2.5)
            .build();
        drive.followTrajectorySequence(trajSeq2);
        intake.open();
        //get cone from stack
        TrajectorySequence trajSeq3 = drive.trajectorySequenceBuilder(trajSeq2.end())
            .back(2.5)
            .strafeLeft(16)
            .turn(Math.toRadians(-169))
            .build();
        drive.followTrajectorySequence(trajSeq3);
        goToTopOfStack(liftLeft, liftRight);
        Trajectory trajSeq4 = drive.trajectoryBuilder(trajSeq3.end(), 80.4828434*0.95*0.6, 73.17330064499293*0.6)
            .forward(26.5)
            .build();
        drive.followTrajectory(trajSeq4);
        intake.close();
        TrajectorySequence trajSeq5 = drive.trajectorySequenceBuilder(trajSeq4.end())
            .waitSeconds(1.5)
            .back(1)
            .build();
        drive.followTrajectorySequence(trajSeq5);
        //low goal
        goToLowGoal(liftLeft, liftRight);
        TrajectorySequence trajSeq6 = drive.trajectorySequenceBuilder(trajSeq5.end())
            .back(20)
            .turn(Math.toRadians(52))
            .forward(7)
            .build();
        drive.followTrajectorySequence(trajSeq6);
        intake.open();
        TrajectorySequence trajSeq7 = drive.trajectorySequenceBuilder(trajSeq6.end())
            .waitSeconds(1)
            .build();
        drive.followTrajectorySequence(trajSeq7);
        TrajectorySequence trajSeq8 = drive.trajectorySequenceBuilder(trajSeq7.end())
            .back(6)
            .turn(Math.toRadians(-55))
            .build();
        drive.followTrajectorySequence(trajSeq8);

        if (parkingZone == 1) { // red
            Trajectory park = drive.trajectoryBuilder(trajSeq8.end())
                .forward(20)
                .build();
            drive.followTrajectory(park);
        } else if (parkingZone == 3) { // blue
            Trajectory park = drive.trajectoryBuilder(trajSeq8.end())
                .back(26)
                .build();
            drive.followTrajectory(park);
        } else {
            Trajectory park = drive.trajectoryBuilder(trajSeq8.end())
                .back(3)
                .build();
            drive.followTrajectory(park);
        }
    }

    public void runAutonRight(Robot drive, HardwareMap hardwareMap) throws InterruptedException {
        DcMotorEx liftLeft = hardwareMap.get(DcMotorEx.class, "leftLift");
        DcMotorEx liftRight = hardwareMap.get(DcMotorEx.class, "rightLift");
        drive.setMotorPowers(0, 0, 0, 0);
        Intake intake = new Intake(hardwareMap);
        intake.close();
        TrajectorySequence trajSeq1 = drive.trajectorySequenceBuilder(new Pose2d())
            .strafeRight(48)
            .build();
        drive.followTrajectorySequence(trajSeq1);
        goToMediumGoal(liftLeft, liftRight);
        TrajectorySequence trajSeq2 = drive.trajectorySequenceBuilder(trajSeq1.end())
            .forward(2)
            .build();
        drive.followTrajectorySequence(trajSeq2);
        intake.open();
        TrajectorySequence trajSeq3 = drive.trajectorySequenceBuilder(trajSeq2.end())
            .back(2)
            .strafeRight(16)
            .turn(Math.toRadians(184))
            .build();
        drive.followTrajectorySequence(trajSeq3);
        goToTopOfStack(liftLeft, liftRight);
        Trajectory trajSeq4 = drive.trajectoryBuilder(trajSeq3.end(), 80.4828434*0.95*0.6, 73.17330064499293*0.6)
            .forward(27)
            .build();
        drive.followTrajectory(trajSeq4);
        intake.close();
        TrajectorySequence trajSeq5 = drive.trajectorySequenceBuilder(trajSeq4.end())
            .waitSeconds(1.5)
            .back(1)
            .build();
        drive.followTrajectorySequence(trajSeq5);
        goToLowGoal(liftLeft, liftRight);
        TrajectorySequence trajSeq6 = drive.trajectorySequenceBuilder(trajSeq5.end())
            .back(20)
            .turn(Math.toRadians(-51.5))
            .forward(4.5)
            .build();
        drive.followTrajectorySequence(trajSeq6);
        intake.open();
        TrajectorySequence trajSeq7 = drive.trajectorySequenceBuilder(trajSeq6.end())
            .waitSeconds(1)
            .build();
        drive.followTrajectorySequence(trajSeq7);
        TrajectorySequence trajSeq8 = drive.trajectorySequenceBuilder(trajSeq7.end())
            .back(6.5)
            .turn(Math.toRadians(48))
            .build();
        drive.followTrajectorySequence(trajSeq8);

        if (parkingZone == 1) { // red
            Trajectory park = drive.trajectoryBuilder(trajSeq8.end())
                .back(24)
                .build();
            drive.followTrajectory(park);
        } else if (parkingZone == 3) { // blue
            Trajectory park = drive.trajectoryBuilder(trajSeq8.end())
                .forward(20)
                .build();
            drive.followTrajectory(park);
        }
    }

    public void goToMediumGoal(DcMotorEx left, DcMotorEx right) {
        liftToPosition(left,right, 620, 620);
    }

    public void goToLowGoal(DcMotorEx left, DcMotorEx right) {
        liftToPosition(left,right, 440, 440);
    }

    public void goToTopOfStack(DcMotorEx left, DcMotorEx right) {
        liftToPosition(left,right, 150, 150);
    }
    private static void liftToPosition(DcMotorEx left, DcMotorEx right,int pos_left, int pos_right)
    {
        double startTime = System.currentTimeMillis();
        while (((right.getCurrentPosition() < pos_right-10 || right.getCurrentPosition() >pos_right+10)&&(left.getCurrentPosition() < pos_left-10 || left.getCurrentPosition() >pos_left+10))){
            right.setTargetPosition(pos_right);
            left.setTargetPosition(pos_left);
            right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            right.setPower(1);
            left.setPower(1);
            if(System.currentTimeMillis()-startTime>3000){
                double startTime2 = System.currentTimeMillis();
                while(true){
                    right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    right.setPower(1);
                    left.setPower(1);

                    if(System.currentTimeMillis()-startTime2>2000) {
                        break;
                    }

                }
                break;
            }
        }
    }
}
