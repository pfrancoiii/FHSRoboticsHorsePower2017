package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by petet on 11/4/2017.
 */

@TeleOp(name="Tank Drive",group="training")
public class tankDriveTeleOp extends OpMode {

    DcMotor leftBackDrive;
    DcMotor leftFrontDrive;
    DcMotor rightBackDrive;
    DcMotor rightFrontDrive;


    @Override
    public void init() {
        leftBackDrive=hardwareMap.dcMotor.get("left_back_drive");
        leftFrontDrive=hardwareMap.dcMotor.get("left_front_drive");
        rightBackDrive=hardwareMap.dcMotor.get("right_back_drive");
        rightFrontDrive=hardwareMap.dcMotor.get("right_front_drive");

    }

    @Override
    public void loop() {

        float LFspeed = gamepad1.left_stick_y - gamepad1.left_stick_x;
        float LBspeed = gamepad1.left_stick_y + gamepad1.left_stick_x;
        float RFspeed = gamepad1.right_stick_y + gamepad1.left_stick_x;
        float RBspeed = gamepad1.right_stick_y - gamepad1.left_stick_x;

        LFspeed = Range.clip(LFspeed, -1, 1);
        LBspeed = Range.clip(LBspeed, -1, 1);
        RFspeed = Range.clip(RFspeed, -1, 1);
        RBspeed = Range.clip(RBspeed, -1, 1);

        leftBackDrive.setPower(LBspeed);
        leftFrontDrive.setPower(LFspeed);
        rightBackDrive.setPower(-RBspeed);
        rightFrontDrive.setPower(-RFspeed);

    }
}

