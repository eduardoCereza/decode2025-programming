package Robô;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AtuadorDoisEstagios_Servos {
    private Servo garra;
    TouchSensor toqueVer, toqueHor;
    private Servo garraVer, garraHor;
    private Servo garraVerY, garraHorY;
    private ElapsedTime delayTimer = new ElapsedTime();
    private boolean delayAtivado = false;

    public AtuadorDoisEstagios_Servos(HardwareMap hardwareMap) {
        garra = hardwareMap.get(Servo.class, "vertical");
        garraVer.setPosition(1.0);
        garraHor.setPosition(1.0);

        garraVerY.setPosition(0.0);
        garraHorY.setPosition(1.0);

        garraVer = hardwareMap.get(Servo.class, "garraVer");
        garraHor = hardwareMap.get(Servo.class, "garraHor");

        garraVerY = hardwareMap.get(Servo.class, "garraVerY");
        garraHorY = hardwareMap.get(Servo.class, "garraHorY");
        //outros servos
    }

    public void abrir() {
        garra.setPosition(1.0);
    }

    public void fechar() {
        garra.setPosition(0.0);
    }

    public void transferir() {
        if (toqueHor.isPressed() && !delayAtivado) {
            garraHorY.setPosition(1.0);
            garraVerY.setPosition(0.0);
            delayTimer.reset();
            delayAtivado = true;
        }

        if (delayAtivado) {
            if (delayTimer.milliseconds() > 1000 && delayTimer.milliseconds() <= 2000) {
                garraHor.setPosition(1.0);
                garraVer.setPosition(0.0);
            } else if (delayTimer.milliseconds() > 2000) {
                garraVerY.setPosition(1.0);
                delayAtivado = false;
            }
        }
    }
}
