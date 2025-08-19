package Programações;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.pedropathing.pathgen.Point;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.LLStatus;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import Robô.AtuadorDoisEstagios_Servos;
import Robô.AtuadorDoisEstagios_VerticalHorizontal;


@Autonomous(name = "Clip México Oficial")
public class AutonomoSpecimen extends OpMode {
    Limelight3A limelight;
    private Servo servo;
    double tx, ty, ta;
    Pose pose;
    AtuadorDoisEstagios_VerticalHorizontal atuador;
    AtuadorDoisEstagios_Servos servos;
    private Follower follower;
    private Timer pathTimer, opmodeTimer;
    private int pathState;
    private PathChain trajetória1, trajetória2, trajetoriapa, trajzi ;
    private final Pose trajsla = new Pose(9.000, 62.000, Point.CARTESIAN);
    private final Pose trajsla2 = new Pose(38.000, 62.000, Point.CARTESIAN);
    private final Pose ponto3 = new Pose(38.000, 62.000, Point.CARTESIAN);
    private final Pose controle= new Pose(22.065, 4.645, Point.CARTESIAN);
    private final Pose control2 = new Pose(61.000, 59.000, Point.CARTESIAN);
    private final Pose ponto4 = new Pose(58.000, 27.000, Point.CARTESIAN);
    private final Pose startPose = new Pose(0, 70, Math.toRadians(180.00));
    private final Pose ClipPose = new Pose(23, 70, Math.toRadians(180.00));
    private final Pose Move1 = new Pose(23, 50, Math.toRadians(180.00));
    private final Pose Move2 = new Pose(40, 50, Math.toRadians(180.00));
    private final Pose Move3 = new Pose(40, 20, Math.toRadians(180.00));

    public void buildPaths() {

        trajetoriapa = follower.pathBuilder()
                .addPath(new BezierLine(new Point(trajsla), new Point(trajsla2)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        trajzi = follower.pathBuilder().addPath(new BezierCurve(new Point(ponto3), new Point(controle), new Point(control2), new Point(ponto4)))
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


        trajetória1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(startPose), new Point(ClipPose)))
                .setConstantHeadingInterpolation(ClipPose.getHeading())

                .setPathEndTimeoutConstraint(0)

                .addPath(new BezierLine(new Point(ClipPose), new Point(Move1)))
                .setConstantHeadingInterpolation(Move1.getHeading())

                .setPathEndTimeoutConstraint(200)
                .addParametricCallback(0.5, () -> atuador.extender_horizontal())

                .build();

        trajetória2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(Move1), new Point(Move2)))
                .setConstantHeadingInterpolation(Move2.getHeading())

                .setPathEndTimeoutConstraint(0)

                .addPath(new BezierLine(new Point(Move2), new Point(Move3)))
                .setConstantHeadingInterpolation(Move3.getHeading())

                .setPathEndTimeoutConstraint(200)
                .addParametricCallback(0.5, () -> servos.abrir())

                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                if (!follower.isBusy()){
                    follower.followPath(trajetoriapa, 1.0, true);
                }
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()){
                    follower.followPath(trajzi, 1.0, true);
                }
                setPathState(2);
                break;
            case 2:
                LLResult result = limelight.getLatestResult();
                if (result != null && result.isValid()) {
                    tx = result.getTx();
                    ta = result.getTa();
                    // entra no controle por visão
                    limelight.getLatestResult().getPythonOutput();
                    }
                break;
        }
    }

    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    //loop
    @Override
    public void loop() {
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

        LLResult result = limelight.getLatestResult();

        pose = follower.getPose();

        if (result != null && result.isValid()) {
            tx = result.getTx(); // How far left or right the target is (degrees)
            ty = result.getTy(); // How far up or down the target is (degrees)
            ta = result.getTa(); // How big the target looks (0%-100% of the image)

            telemetry.addData("Target X", tx);
            telemetry.addData("Target Y", ty);
            telemetry.addData("Target Area", ta);
        } else {
            telemetry.addData("Limelight", "No Targets");
        }

        servos.transferir();
        follower.update();
        autonomousPathUpdate();
    }

    @Override
    public void init() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.pipelineSwitch(0); // Switch to pipeline number 0
        limelight.start(); // This tells Limelight to start looking!


        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower =  new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        servo = hardwareMap.get(Servo.class, "servo1");
        servo.setDirection(Servo.Direction.REVERSE);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {

    }
}