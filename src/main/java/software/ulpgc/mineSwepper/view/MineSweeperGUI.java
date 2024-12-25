package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Factories.Factory;
import software.ulpgc.mineSwepper.Factories.MineSweeperGUICommandFactory;
import software.ulpgc.mineSwepper.Listeners.ResetGameListener;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class MineSweeperGUI extends JFrame {
    private final Map<String, Command> commands;
    private final JButton[][] buttons;
    private final Factory factory;
    private boolean firstClick = false;
    private final JFrame frame = new JFrame("MineSweeper");

    public MineSweeperGUI(Board board, ResetGameListener resetGameListener) {
        this.buttons = new JButton[board.cells.getRows()][board.cells.getColumns()];
        this.factory = MineSweeperGUICommandFactory.CreateCommandFactoryWith(board, this.buttons, resetGameListener);
        this.commands = factory.factorize();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(board.cells.getRows(), board.cells.getColumns()));
        addButtonsToFrame(frame, board);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addButtonsToFrame(JFrame frame, Board board) {
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    JButton button = createButton();
                    button.addActionListener(e -> getCommandsForButton(new CellLocation(i,j)));
                    this.buttons[i][j] = button;
                    frame.add(button);
                })
        );
    }

    private void getCommandsForButton(CellLocation cellLocation) {
        if (!firstClick) generateMines(cellLocation);
        asList("GenerateMines", "CalculateAdjacentMines",
                "Reveal:" + cellLocation.x() + ":" + cellLocation.y(),"CheckWinCondition")
                .forEach(s -> commands.get(s).execute());
    }

    private void generateMines(CellLocation cellLocation) {
        factory.createCommandOnDemand(cellLocation);
        firstClick = true;
    }

    private static JButton createButton() {
        JButton button = new JButton();
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        return button;
    }

    public JFrame getFrame() {return frame;}
}
