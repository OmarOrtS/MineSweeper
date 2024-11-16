package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellIterator;
import software.ulpgc.mineSwepper.model.Point;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MineSweeperGUI extends JFrame {
    private final Board board;
    private final JButton[][] buttons;
    private final Map<String, Command> commands;

    public MineSweeperGUI(Board board) {
        this.board = board;
        this.commands = new HashMap<>();
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(boardRows(), boardColumns()));
        this.buttons = addButtonsToFrame(frame);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private JButton[][] addButtonsToFrame(JFrame frame) {
        JButton[][] buttons = new JButton[boardRows()][boardColumns()];
        for (int i = 0; i < boardRows(); i++)
            for (int j = 0; j < boardColumns(); j++) {
                JButton button = createButton();
                final int x = i, y = j;
                button.addActionListener(e -> revealCell(x, y));
                buttons[i][j] = button;
                frame.add(button);
            }
        return buttons;
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
                //propagateReveal(x, y);
            }
        }
        //checkWinCondition();
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

    private static JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

    private int boardColumns() { return board.cells.getColumns(); }

    private int boardRows() { return board.cells.getRows(); }

    public Command put(String key, Command value) { return commands.put(key, value); }

    public Board getBoard() { return board; }

    public JButton[][] getButtons() { return buttons; }
}
