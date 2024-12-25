package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameStateListener;

import javax.swing.*;

public class GameDialogDisplay {
    private final GameStateListener gameStateListener;

    public GameDialogDisplay(GameStateListener gameStateListener) {
        this.gameStateListener = gameStateListener;
    }

    public void showEndGameDialog(String message) {
        switch (showOptionDialog(message)) {
            case JOptionPane.YES_OPTION -> gameStateListener.notifyGameRestartRequest();
            case JOptionPane.NO_OPTION -> gameStateListener.notifyCloseGameRequest();
        }
    }

    private static int showOptionDialog(String message) {
        return JOptionPane.showOptionDialog(
                null,
                message + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Restart", "Exit"},
                "Restart"
        );
    }
}
