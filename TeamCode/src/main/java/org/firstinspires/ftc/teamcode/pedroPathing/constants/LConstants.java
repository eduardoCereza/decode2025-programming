package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        //Definir distancia dos PODS do centro do robô
        PinpointConstants.forwardY = 1;
        PinpointConstants.strafeX = -2.5;
        //Definir a unidade de medida para medir a distancia
        PinpointConstants.distanceUnit = DistanceUnit.CM;
        //nome do pinpoint no DRIVE HUB
        PinpointConstants.hardwareMapName = "pinpoint";
        //"Escalar de guinada"
        PinpointConstants.useYawScalar = false;
        PinpointConstants.yawScalar = 1.0;
        //Se você estiver usando uma resolução de codificador personalizada
        PinpointConstants.useCustomEncoderResolution = false;
        //Resolução do enconder
        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
        PinpointConstants.customEncoderResolution = 13.26291192;
        /*
        Definir a direção do X e Y.

        Se o valor de X cai quando o robô se move para a frente, inverte a
        direção alterando o  GoBildaPinpointDriver.EncoderDirection (fowardEncoder).

        Se o valor de Y diminui quando o robô se move para a esquerda, inverta a direção mudando
        o GoBildaPinpointDriver.EncoderDirection (strafeEncoder).
        */
        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;
        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;

    }
}




