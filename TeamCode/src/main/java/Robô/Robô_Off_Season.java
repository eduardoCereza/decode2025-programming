package Robô;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Robô_Off_Season {
    private Servo garra, ponta;
    private DcMotorEx Esq, Dir;
    private DcMotorEx slide;

    public Robô_Off_Season(HardwareMap hardwareMap){
        Esq = hardwareMap.get(DcMotorEx.class, "vertical");
        Esq.setDirection(DcMotorSimple.Direction.REVERSE);
        Esq.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Esq.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        Esq.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        Dir = hardwareMap.get(DcMotorEx.class, "vertical");
        Dir.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Dir.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        Dir.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slide = hardwareMap.get(DcMotorEx.class, "slide");
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        garra = hardwareMap.get(Servo.class, "garra");
        ponta = hardwareMap.get(Servo.class, "ponta");
    }

    public void levantar(){
        Dir.setTargetPosition(1000);
        Dir.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        Dir.setPower(1.0);

        Esq.setTargetPosition(1000);
        Esq.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        Esq.setPower(1.0);
    }
    public void descer(){
        Dir.setTargetPosition(0);
        Dir.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        Dir.setPower(1.0);

        Esq.setTargetPosition(0);
        Esq.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        Esq.setPower(1.0);

        Dir.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Esq.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void garra(double pos){

    }
    public void pegar(){}
    public void clipar(){}
    public void specimen(){}

}
