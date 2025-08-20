package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        DriveEncoderConstants.forwardTicksToInches = -0.00646213234;
        DriveEncoderConstants.strafeTicksToInches = -0.0334294865;
        DriveEncoderConstants.turnTicksToInches = 0.03485;

        DriveEncoderConstants.robot_Width = 16.9291;
        DriveEncoderConstants.robot_Length = 17.3228;

        DriveEncoderConstants.leftFrontEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightFrontEncoderDirection = Encoder.FORWARD;
        DriveEncoderConstants.leftRearEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightRearEncoderDirection = Encoder.FORWARD;
    }
}




