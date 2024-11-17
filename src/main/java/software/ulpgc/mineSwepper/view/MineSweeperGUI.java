package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.control.CommandHandler;
import software.ulpgc.mineSwepper.control.revealCellCommandHandler;
import software.ulpgc.mineSwepper.control.RevealCellCommand;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class MineSweeperGUI extends JFrame {
    private final JButton[][] buttons;
    private final CommandHandler handler;

    public MineSweeperGUI(Board board) {
        this.handler = new revealCellCommandHandler();
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(board.cells.getRows(), board.cells.getColumns()));
        this.buttons = addButtonsToFrame(frame, board);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private JButton[][] addButtonsToFrame(JFrame frame, Board board) {
        JButton[][] buttons = new JButton[board.cells.getRows()][board.cells.getColumns()];
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    JButton button = createButton();
                    String commandKey = "Reveal:" + i + ":" + j;
                    handler.registerCommand(commandKey,
                            new RevealCellCommand(new BoardController(board, buttons), new CellLocation(i, j)));
                    button.addActionListener(e -> handler.handle(commandKey));
                    buttons[i][j] = button;
                    frame.add(button);
                })
        );
        return buttons;
    }

    private static JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

}
