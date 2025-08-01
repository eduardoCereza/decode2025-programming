package Programações;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@TeleOp(name = "teleoperadodoisestagios", group = "Examples")
public class TeleoperadoSp extends OpMode {

    private DcMotorEx Esq, Dir;
    //eixo e abrie e fechar
    Servo garra, ponta;
    private Follower follower;
    boolean holdingPosition = false;
    private final Pose startPose = new Pose(0,0,0);

    @Override
    public void init() {
        Esq = hardwareMap.get(DcMotorEx.class, "Esq");
        Esq.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Esq.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        Esq.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        Dir = hardwareMap.get(DcMotorEx.class, "Dir");
        Dir.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        Dir.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        Dir.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        garra = hardwareMap.get(Servo.class, "garra");
        ponta = hardwareMap.get(Servo.class, "ponta");

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

        //fechar
        ponta.setPosition(1.0);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    @Override
    public void loop(){

        subir();
        descer();

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */
        telemetry.update();
    }

    @Override
    public void stop() {}
    public void abrir(){
        //botar botão depois
        if(gamepad2.y){

        }
    }
    

    public void base(){

        double j = -gamepad2.right_stick_y;
        int currentL = armMotorL.getCurrentPosition();
        int currentR = armMotorR.getCurrentPosition();

        // Se o joystick for movido para cima e a posição for menor que 0, move o motor
        if (j > 0) {
            armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorL.setPower(0.35);

            armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorR.setPower(0.35);

            modeBase = false; // O motor está se movendo, então não está segurando posição
        }
        // Se o joystick for movido para baixo e ainda não atingiu o limite, move o motor
        else if (j < 0) {
            armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorL.setPower(-0.22);

            armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorR.setPower(-0.22);
            modeBase = false; // O motor está se movendo, então não está segurando posição
        }
        // Se o joystick estiver parado e o motor ainda não estiver segurando a posição
        else if (!modeBase) {

            armMotorL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            armMotorR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // O operador ! (negação) verifica se holdingPosition é false
            armMotorL.setTargetPosition(currentL); // Define a posição atual como alvo
            armMotorL.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Mantém o motor na posição
            armMotorL.setPower(1); // Aplica uma pequena potência para segurar a posição

            armMotorR.setTargetPosition(currentR); // Define a posição atual como alvo
            armMotorR.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Mantém o motor na posição
            armMotorR.setPower(1);

            modeBase = true; // Marca que o motor está segurando a posição
        }

        if(gamepad2.dpad_up){
            armMotorL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armMotorR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
            }
    public void atuador(){

        int current = slide.getCurrentPosition();
        int limit = -2990;
        double joystickInput = gamepad2.left_stick_y; // Captura a entrada do joystick

        // Se o joystick for movido para cima e a posição for menor que 0, move o motor
        if (joystickInput > 0 && current < 0) {
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setPower(joystickInput);
            holdingPosition = false; // O motor está se movendo, então não está segurando posição
        }
        // Se o joystick for movido para baixo e ainda não atingiu o limite, move o motor
        else if (joystickInput < 0 && current > limit) {
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide.setPower(joystickInput);
            holdingPosition = false; // O motor está se movendo, então não está segurando posição
        }
        // Se o joystick estiver parado e o motor ainda não estiver segurando a posição
        else if (!holdingPosition) { // O operador ! (negação) verifica se holdingPosition é false
            slide.setTargetPosition(current); // Define a posição atual como alvo
            slide.setMode(DcMotor.RunMode.RUN_TO_POSITION); // Mantém o motor na posição
            slide.setPower(0.3); // Aplica uma pequena potência para segurar a posição
            holdingPosition = true; // Marca que o motor está segurando a posição
        }

        if(gamepad2.x){
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

}
