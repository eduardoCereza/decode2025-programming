package Programações;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name="Autonomous Cesta")
public class AutonomoCesta extends OpMode {

    Timer pathTimer, opmodeTimer;
    private int pathState;
    Pose pose;
    Follower follower;

    
    public void buildPaths(){

    }

    public void autonomousPathUpdate(){

    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    //Início da função de incialização
    public void init(){

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower =  new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

    }
    //Início do loop

    public void loop(){

    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

}
