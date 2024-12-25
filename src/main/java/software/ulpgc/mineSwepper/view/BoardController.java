package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.ResetGameListener;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BoardController {
    private final Board board;
    private final JButton[][] buttons;
    private final ResetGameListener resetGameListener;

    public BoardController(Board board, JButton[][] buttons, ResetGameListener resetGameListener) {
        this.board = board;
        this.buttons = buttons;
        this.resetGameListener = resetGameListener;
    }

    public void revealCell(CellLocation cellLocation) {
        Cell cell = board.cells.getCells()[cellLocation.x()][cellLocation.y()];

        if (cell.isRevealed()) return;

        Cell revealedCell = cell.reveal();
        board.cells.getCells()[cellLocation.x()][cellLocation.y()] = revealedCell;

        JButton button = buttons[cellLocation.x()][cellLocation.y()];
        if (revealedCell.isMine()) {
            button.setText("💣");
            button.setBackground(Color.RED);
            showEndGameDialog("Game Over! You hit a mine!");
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

    public void showEndGameDialog(String message) {
        switch (showOptionDialog(message)) {
            case JOptionPane.YES_OPTION -> resetGame();
            case JOptionPane.NO_OPTION -> System.exit(0);
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

    private void resetGame() {resetGameListener.onGameReset();}

    public Board getBoard() {return board;}

}
