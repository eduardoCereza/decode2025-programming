package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        DriveEncoderConstants.forwardTicksToInches = -0.00578236958;
        DriveEncoderConstants.strafeTicksToInches = -0.00708145886;
        DriveEncoderConstants.turnTicksToInches = -0.0317666667;

        DriveEncoderConstants.robot_Width = 16.9291;
        DriveEncoderConstants.robot_Length = 16.9291;

        DriveEncoderConstants.leftFrontEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightFrontEncoderDirection = Encoder.FORWARD;
        DriveEncoderConstants.leftRearEncoderDirection = Encoder.REVERSE;
        DriveEncoderConstants.rightRearEncoderDirection = Encoder.FORWARD;
    }
}




