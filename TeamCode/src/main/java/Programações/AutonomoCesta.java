package Programações;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name="Autonomous Cesta")
public class AutonomoCesta extends OpMode {

    Timer pathTimer, opmodeTimer;
    private int pathState;
    Pose pose;
    Follower follower;

    int lastError;

    DcMotor leftArm, rightArm;

    private final Pose startPose = new Pose(0, 0, Math.toRadians(180));
    public void buildPaths(){

    }

    public void autonomousPathUpdate(){

        switch (pathState){
            case 0:{
                moverAtuador(-250);
                setPathState(1);
            }
        }

    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    //Início da função de incialização
    public void init(){

        //Inicializar Pedro Pathing
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower =  new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        //Inicializar atuador
        leftArm = hardwareMap.get(DcMotor.class, "Esq");
        rightArm = hardwareMap.get(DcMotor.class, "Dir");

        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }
    //Início do loop

    public void loop(){
        follower.update();
        autonomousPathUpdate();

        moverAtuador(-250);
    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    public void moverAtuador(/*double kp,*/ int target){
        int encoderPosition = -leftArm.getCurrentPosition();
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (encoderPosition < (-target)){
            leftArm.setPower(1);
            rightArm.setPower(1);
        }else{
            leftArm.setPower(-1);
            rightArm.setPower(-1);
        }

        telemetry.addData("Encoder Position: ", encoderPosition);
        telemetry.addData("Target: ", target);
    }
}
