package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellIterator;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class BoardController {
    private final Board board;
    private final JButton[][] buttons;

    public BoardController(Board board, JButton[][] buttons) {
        this.board = board;
        this.buttons = buttons;
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
            JOptionPane.showMessageDialog(null, "Game Over! You hit a mine!");
            //resetGame();
        } else {
            int adjacentMines = revealedCell.getAdjacentMine();
            button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);

            if (adjacentMines == 0) {
                propagateReveal(cellLocation);
            }
        }
        //checkWinCondition();
    }

    private void propagateReveal(CellLocation cellLocation) {
        int[] directions = {-1, 0, 1};
        Arrays.stream(directions).forEach(dx -> Arrays.stream(directions).forEach(dy -> {
            if (dx == 0 && dy == 0) return;
            if (cellIsWithinLimits(new CellLocation(cellLocation.x()+dx, cellLocation.y()+dy))
                    && cellIsNotRevealed(new CellLocation(cellLocation.x()+dx, cellLocation.y()+dy)))
                revealCell(new CellLocation(cellLocation.x() + dx, cellLocation.y() + dy));
        }));
    }

    private boolean cellIsNotRevealed(CellLocation cellLocation) {return !board.cells.getCells()[cellLocation.x()][cellLocation.y()].isRevealed(); }

    private boolean cellIsWithinLimits(CellLocation cellLocation) {
        return cellLocation.x() >= 0 && cellLocation.x() < board.cells.getRows() && cellLocation.y() >= 0 && cellLocation.y() < board.cells.getColumns();
    }

    private void checkWinCondition() {
        int revealedCells = 0;
        int totalCells = board.cells.getRows() * board.cells.getColumns();
        CellIterator cellIterator = new CellIterator(board.cells);
        while (cellIterator.hasNext())
            if (cellIterator.next().isRevealed()) revealedCells++;

        if (revealedCells == totalCells - board.totalMines()) {
            JOptionPane.showMessageDialog(null, "Congratulations! You won!");
            //resetGame();
        }
    }

}
