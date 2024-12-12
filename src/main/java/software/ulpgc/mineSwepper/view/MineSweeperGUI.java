package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Factories.CommandFactory;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.IntStream;

public class MineSweeperGUI extends JFrame {
    private final Map<String, Command> commands;
    private final JButton[][] buttons;

    public MineSweeperGUI(Board board) {
        this.buttons = new JButton[board.cells.getRows()][board.cells.getColumns()];
        this.commands = CommandFactory.createCommands(board, this.buttons);
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(board.cells.getRows(), board.cells.getColumns()));
        addButtonsToFrame(frame, board);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private void addButtonsToFrame(JFrame frame, Board board) {
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    JButton button = createButton();
                    button.addActionListener(e -> commands.get("Reveal:" + i + ":" + j).execute());
                    this.buttons[i][j] = button;
                    frame.add(button);
                })
        );
    }

    private static JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

}
