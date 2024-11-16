package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellIterator;
import software.ulpgc.mineSwepper.model.Point;

import javax.swing.*;
import java.awt.*;

public class RevealCellCommand implements Command{
    private final Board board;
    private final JButton[][] buttons;

    public RevealCellCommand(Board board, JButton[][] buttons) {
        this.board = board;
        this.buttons = buttons;
    }

    @Override
    public void execute() {
        //revealCell();
    }

    private void revealCell(int x, int y) {
        if (board.cells.getCells()[x][y].isRevealed()) return;
        board.cells.getCells()[x][y].revealCell(new Point(x, y), board.cells);
        JButton button = buttons[x][y];
        if (board.cells.getCells()[x][y].isMine()) {
            button.setText("💣");
            button.setBackground(Color.RED);
            JOptionPane.showMessageDialog(null, "Game Over! You hit a mine!");
            //resetGame();
        } else {
            int adjacentMines = board.cells.getCells()[x][y].getAdjacentMine();
            button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
            button.setEnabled(false);
            button.setBackground(Color.LIGHT_GRAY);

            if (adjacentMines == 0) {
                propagateReveal(x, y);
            }
        }
        checkWinCondition();
    }

    private void propagateReveal(int x, int y) {
        for (Point point : board.cells.getCells()[x][y].getAdjacentCell(new Point(x, y))) {
            if (point.x() >= 0 && point.x() < board.cells.getRows() && point.y() >= 0 && point.y() < board.cells.getColumns()) {
                revealCell(point.x(), point.y());
            }
        }
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
