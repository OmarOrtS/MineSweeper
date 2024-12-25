package software.ulpgc.mineSwepper;

import software.ulpgc.mineSwepper.Listeners.ResetGameListener;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Matrix;
import software.ulpgc.mineSwepper.view.MineSweeperGUI;

public class Main implements ResetGameListener {
    private MineSweeperGUI gameScreen;
    public static void main(String[] args) {
        Main main = new Main();
        main.startGame();
    }

    private void startGame() {
        disposeGameInstance();
        gameScreen = new MineSweeperGUI(Board.createBoard(new Matrix(16, 16), 40), this);
    }

    private void disposeGameInstance() {
        if (gameScreen != null) gameScreen.getFrame().dispose();
    }

    @Override
    public void onGameReset() {
        startGame();
    }

}
