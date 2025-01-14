package software.ulpgc.mineSwepper;

import software.ulpgc.mineSwepper.Listeners.GameMasterListener;
import software.ulpgc.mineSwepper.Loaders.ImageIconIconLoader;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Difficulty;
import software.ulpgc.mineSwepper.model.Dimension;
import software.ulpgc.mineSwepper.model.Matrix;
import software.ulpgc.mineSwepper.view.MineSweeperGUI;

import javax.swing.*;

public class Main implements GameMasterListener {
    private MineSweeperGUI gameScreen;
    public static void main(String[] args) {
        Main main = new Main();
        main.startGame();
    }

    @Override
    public void onGameReset() {startGame();}

    @Override
    public void onExit() {System.exit(0);}

    private void startGame() {
        Difficulty difficulty = selectDifficulty();
        if (difficulty == null) System.exit(1);
        disposeGameInstance();
        gameScreen = new MineSweeperGUI(Board.createBoard(new Matrix(difficulty.getRows(), difficulty.getColumns()),
                difficulty.getMines()), this);
    }

    private static Difficulty selectDifficulty() {
        String[] options = {"Easy", "Medium", "Hard"};
        ImageIcon icon = new ImageIconIconLoader("/MineSweeper.ico", new Dimension(5,5)).load();
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select Difficulty:",
                "Minesweeper Difficulty",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                options,
                options[0]
        );

        return switch (choice){
            case 0 -> Difficulty.EASY;
            case 1 -> Difficulty.MEDIUM;
            case 2 -> Difficulty.HARD;
            default -> null;
        };
    }

    private void disposeGameInstance() {if (gameScreen != null) gameScreen.getFrame().dispose();}

}
