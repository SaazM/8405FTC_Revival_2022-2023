package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotorEx leftLift;
    public DcMotorEx rightLift;

    public double startTime;
    public int holdingPosLeft;
    public int holdingPosRight;
    public boolean kill;
    public boolean liftReached = true;//only used in auton
    public boolean isHolding = false;


    public Lift(HardwareMap hardwareMap) {



        rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");


        leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightLift.setDirection(DcMotor.Direction.FORWARD);
        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        startTime = System.currentTimeMillis();
        holdingPosLeft = -1;
        holdingPosRight = -1;
        kill = false;
    }

    public void liftToPosition(int posLeft, int posRight, double power)
    {
        liftReached = (Math.abs(rightLift.getCurrentPosition() - posRight) < 20) || (Math.abs(leftLift.getCurrentPosition() - posLeft) < 20);
        rightLift.setTargetPosition(posLeft);
        leftLift.setTargetPosition(posRight);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift.setPower(1);
        leftLift.setPower(1);
    }

    public void macros(Gamepad gamepad1) {
        double temp = startTime;
        boolean temp_ = kill;
        boolean tempHolding = isHolding;
        startTime = System.currentTimeMillis();
        kill = false;
        isHolding = false;
        if (gamepad1.square) {
            holdingPosLeft = 680;
            holdingPosRight = 680;
        } else if (gamepad1.circle) {
            holdingPosLeft = 380;
            holdingPosRight = 380;
        } else if (gamepad1.dpad_up) {
            holdingPosLeft = 135;
            holdingPosRight = 135;
        } else if (gamepad1.right_trigger > 0.5) {

            rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setPower(0.8);
            rightLift.setPower(0.8);
            holdingPosLeft = -1;
            holdingPosRight = -1;
        } else if (gamepad1.left_trigger > 0.5) {
            rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftLift.setPower(-0.5);
            rightLift.setPower(-0.5);
            holdingPosLeft = -1;
            holdingPosRight = -1;
        } else if (rightLift.getCurrentPosition() > 30 && rightLift.getMode() == DcMotor.RunMode.RUN_USING_ENCODER) {
            isHolding = true;
            if (holdingPosLeft == -1) {
                holdingPosLeft = leftLift.getCurrentPosition();
                holdingPosRight = rightLift.getCurrentPosition();
            }
        }
        else
        {
            kill = temp_;
            startTime = temp;
            isHolding = tempHolding;
        }
        if(gamepad1.right_bumper || ((System.currentTimeMillis() - startTime)>15000)) {
            holdingPosRight = 0;
            holdingPosLeft = 0;
            liftToPosition(0, 0, 0);
            rightLift.setPower(0);
            leftLift.setPower(0);
            kill = true;
        }
        if(holdingPosLeft != -1 && !kill) {
            if (isHolding) {
                liftToPosition(holdingPosLeft, holdingPosRight, 0.05);
            } else {
                liftToPosition(holdingPosLeft, holdingPosRight, 0.8);
            }
        }
    }
    public void goToMediumGoal() { liftToPosition(630, 630, 0.8); }

    public void goToLowGoal()  { liftToPosition(380, 380, 0.8); }

    public void goToHighGoal() { liftToPosition(700, 700, 0.8); }

    public void liftToMedium() { liftToPosition(630, 630, 0.8); }

    public void liftToLow() { liftToPosition(380, 380, 0.8); }

    public void liftToTopStack() { liftToPosition(180, 180, 0.8); }


    public void reset() {
        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}