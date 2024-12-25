package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameStateListener;
import software.ulpgc.mineSwepper.Listeners.GameManagerListener;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BoardController implements GameStateListener {
    private final Board board;
    private final JButton[][] buttons;
    private final GameManagerListener gameManagerListener;
    private final GameDialogDisplay gameDialogDisplay;

    public BoardController(Board board, JButton[][] buttons, GameManagerListener gameManagerListener) {
        this.board = board;
        this.buttons = buttons;
        this.gameManagerListener = gameManagerListener;
        gameDialogDisplay = new GameDialogDisplay(this);
    }

    @Override
    public void notifyGameRestartRequest() {resetGame();}

    @Override
    public void notifyCloseGameRequest() {closeGame();}

    private void closeGame() {gameManagerListener.onExit();}

    private void resetGame() {gameManagerListener.onGameReset();}

    public void revealCell(CellLocation cellLocation) {
        Cell cell = board.cells.getCells()[cellLocation.x()][cellLocation.y()];

        if (cell.isRevealed()) return;

        Cell revealedCell = cell.reveal();
        board.cells.getCells()[cellLocation.x()][cellLocation.y()] = revealedCell;

        JButton button = buttons[cellLocation.x()][cellLocation.y()];
        if (revealedCell.isMine()) {
            button.setText("💣");
            button.setBackground(Color.RED);
            gameDialogDisplay.showEndGameDialog("Game Over! You hit a mine!");
        } else {
            int adjacentMines = revealedCell.getAdjacentMine();
            button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);

            if (adjacentMines == 0) {
                propagateReveal(cellLocation);
            }
        }
    }

    public void placeFlag(JButton button) {button.setText(button.getText().equals("🚩") ? "" : "🚩");}

    private void propagateReveal(CellLocation cellLocation) {
        int[] directions = {-1, 0, 1};
        Arrays.stream(directions).forEach(x -> Arrays.stream(directions).forEach(y -> {
            if (x == 0 && y == 0) return;
            if (cellIsValidToReval(cellLocation, x, y))
                revealCell(nextCell(cellLocation, x, y));
        }));
    }

    private boolean cellIsValidToReval(CellLocation cellLocation, int x, int y) {
        return cellIsWithinLimits(nextCell(cellLocation, x, y))
                && cellIsNotRevealed(nextCell(cellLocation, x, y));
    }

    private static CellLocation nextCell(CellLocation cellLocation, int x, int y) {
        return new CellLocation(cellLocation.x() + x, cellLocation.y() + y);
    }

    private boolean cellIsNotRevealed(CellLocation cellLocation) {return !board.cells.getCells()[cellLocation.x()][cellLocation.y()].isRevealed(); }

    private boolean cellIsWithinLimits(CellLocation cellLocation) {
        return cellLocation.x() >= 0 && cellLocation.x() < board.cells.getRows() && cellLocation.y() >= 0 && cellLocation.y() < board.cells.getColumns();
    }

    public Board getBoard() {return board;}

    public GameDialogDisplay display() {return gameDialogDisplay;}
}
