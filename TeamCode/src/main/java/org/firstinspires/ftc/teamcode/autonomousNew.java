package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by petet on 11/18/2017.
 */
@Autonomous(name="pushBot",group="Pushbot")
public class autonomousNew extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();

    ColorSensor colorSensor;
    DcMotor leftBackDrive;
    DcMotor leftFrontDrive;
    DcMotor rightBackDrive;
    DcMotor rightFrontDrive;
  //  Servo armServo;

    static final double counts_per_motor_REV = 1220;
    static final double drive_gear_reduction = 1.0;
    static final double wheel_diameter_inches = 4.0;
    static final double count_per_inch = (counts_per_motor_REV * drive_gear_reduction)/(wheel_diameter_inches*3.1415);
    static final double drive_speed = 1.0;
    static final double turn_speed=0.5;



    @Override
    public void runOpMode() {
        leftBackDrive=hardwareMap.dcMotor.get("left_back_drive");
        leftFrontDrive=hardwareMap.dcMotor.get("left_front_drive");
        rightBackDrive=hardwareMap.dcMotor.get("right_back_drive");
        rightFrontDrive=hardwareMap.dcMotor.get("right_front_drive");

        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        colorSensor.enableLed(true);

      //  armServo = hardwareMap.get(Servo.class, "arm_servo");

        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

      //  armDown(2.0);
        jewel(5.0);
        encoderDrive(drive_speed, 15, 15, 5.0);
        encoderDrive(turn_speed, 6, -6, 4.0);
        encoderDrive(drive_speed, 3, 3, 4.0);

    }

    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newLeftBackTarget;
        int newLeftFrontTarget;
        int newRightBackTarget;
        int newRightFrontTarget;

        if(opModeIsActive()) {

            newLeftBackTarget = leftBackDrive.getCurrentPosition() + (int) (leftInches *count_per_inch);
            newLeftFrontTarget = leftFrontDrive.getCurrentPosition() + (int) (leftInches *count_per_inch);
            newRightBackTarget = rightBackDrive.getCurrentPosition() + (int) (rightInches * count_per_inch);
            newRightFrontTarget = rightFrontDrive.getCurrentPosition() + (int) (rightInches *count_per_inch);
            leftBackDrive.setTargetPosition(newLeftBackTarget);
            leftFrontDrive.setTargetPosition(newLeftFrontTarget);
            rightBackDrive.setTargetPosition(newRightBackTarget);
            rightFrontDrive.setTargetPosition(newRightFrontTarget);

            // Turn On RUN_TO_POSITION
            leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            leftBackDrive.setPower(speed);
            leftFrontDrive.setPower(speed);
            rightBackDrive.setPower(-speed);
            rightFrontDrive.setPower(-speed);

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftBackDrive.isBusy() && rightBackDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftBackTarget, newRightBackTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        leftBackDrive.getCurrentPosition(),
                        rightBackDrive.getCurrentPosition());
                telemetry.update();
            }



            leftBackDrive.setPower(0);
            leftFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
            rightFrontDrive.setPower(0);

            leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    }
    public void jewel(double holdTime){
        ElapsedTime holdTimer = new ElapsedTime();
        holdTimer.reset();
        while (opModeIsActive() && holdTimer.time() < holdTime) {
            if (colorSensor.blue() > 3) {
                encoderDrive(turn_speed, -2, 2, 2.0);
                encoderDrive(turn_speed, 2, -2, 2.0);
             //   armServo.setPosition(0.0);
            } else {
                encoderDrive(turn_speed, 2, -2, 2.0);
                encoderDrive(turn_speed, -2, 2, 2.0);
            //   armServo.setPosition(0.0);

            }
            leftBackDrive.setPower(0);
            leftFrontDrive.setPower(0);
            rightBackDrive.setPower(0);
            rightFrontDrive.setPower(0);
        }
    }
  //  public void armDown(double holdTime) {
   //     ElapsedTime holdTimer = new ElapsedTime();
     //   holdTimer.reset();
     //   while (opModeIsActive() && holdTimer.time() < holdTime) {
    //        armServo.setPosition(1.0);
    //    }
  //  }
}

