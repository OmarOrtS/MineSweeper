package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.control.CommandHandler;
import software.ulpgc.mineSwepper.control.revealCellCommandHandler;
import software.ulpgc.mineSwepper.control.RevealCellCommand;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Point;

import javax.swing.*;
import java.awt.*;

public class MineSweeperGUI extends JFrame {
    private final BoardController controller;
    private final JButton[][] buttons;
    private final CommandHandler handler;

    public MineSweeperGUI(Board board) {
        this.handler = new revealCellCommandHandler();
        JFrame frame = new JFrame("MineSweeper");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(board.cells.getRows(), board.cells.getColumns()));
        this.buttons = addButtonsToFrame(frame, board);
        this.controller = new BoardController(board, this.buttons);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    private JButton[][] addButtonsToFrame(JFrame frame, Board board) {
        JButton[][] buttons = new JButton[board.cells.getRows()][board.cells.getColumns()];
        for (int i = 0; i < board.cells.getRows(); i++)
            for (int j = 0; j < board.cells.getColumns(); j++) {
                JButton button = createButton();
                handler.registerCommand("Reveal:"+i+":"+j, new RevealCellCommand(new BoardController(board, buttons), new Point(i,j)));
                final int x = i, y = j;
                button.addActionListener(e -> handler.handle("Reveal:"+x+":"+y));
                buttons[i][j] = button;
                frame.add(button);
            }
        return buttons;
    }

    private static JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

}
