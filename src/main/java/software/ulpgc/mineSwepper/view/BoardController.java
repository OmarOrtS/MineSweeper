package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellIterator;
import software.ulpgc.mineSwepper.model.Point;

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

    public void revealCell(Point point) {
        Cell cell = board.cells.getCells()[point.x()][point.y()];

        if (cell.isRevealed()) return;

        cell.reveal();

        JButton button = buttons[point.x()][point.y()];
        if (cell.isMine()) {
            button.setText("💣");
            button.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "Game Over! You hit a mine!");
            //resetGame();
        } else {
            int adjacentMines = cell.getAdjacentMine();
            button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);

            if (adjacentMines == 0) {
                //propagateReveal(point.x(), point.y());
            }
        }
        //checkWinCondition();
    }

    private void propagateReveal(Point point) {
        int[] directions = {-1, 0, 1};
        Arrays.stream(directions).forEach(dx -> Arrays.stream(directions).forEach(dy -> {
            int newX = point.x() + dx;
            int newY = point.y() + dy;
            if (newX >= 0 && newX < board.cells.getRows() && newY >= 0 && newY < board.cells.getColumns()
                    && !board.cells.getCells()[newX][newY].isRevealed()) {
                revealCell(new Point(newX, newY));
            }
        }));
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

    public Board board() { return board; }
}
