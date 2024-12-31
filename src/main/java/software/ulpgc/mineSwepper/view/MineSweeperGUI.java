package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Factories.Factory;
import software.ulpgc.mineSwepper.Factories.MineSweeperGUICommandFactory;
import software.ulpgc.mineSwepper.Listeners.GameMasterListener;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

public class MineSweeperGUI extends JFrame {
    private final Map<String, Command> commands;
    private final Factory factory;
    private boolean firstClick = false;
    private final JFrame frame = new JFrame("MineSweeper");

    public MineSweeperGUI(Board board, GameMasterListener gameMasterListener) {
        JButton[][] buttons = new JButton[board.cells.getRows()][board.cells.getColumns()];
        BoardController controller = new BoardController(board, buttons, gameMasterListener);
        this.factory = MineSweeperGUICommandFactory.CreateCommandFactoryWith(controller);
        this.commands = factory.factorize();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(board.cells.getRows(), board.cells.getColumns()));
        addButtonsToFrame(frame, board, controller, buttons);
        frame.setSize(1300, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        controller.loadIconsForButtons(buttons);
        addResizeListener(controller);
    }

    private void addResizeListener(BoardController controller) {
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                controller.adjustIconsToNewScreenSize();
                controller.adjustButtonsFont();
            }
        });
    }

    private void addButtonsToFrame(JFrame frame, Board board, BoardController controller, JButton[][] buttons) {
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    JButton button = createButton();
                    button.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            CellLocation cellLocation = new CellLocation(i, j);
                            if (isRevealed(cellLocation)) return;
                            switch (e.getButton()) {
                                case MouseEvent.BUTTON1 -> getCommandsForButton(cellLocation);
                                case MouseEvent.BUTTON3 -> controller.placeFlag(button);
                            }
                        }

                        private boolean isRevealed(CellLocation cellLocation) {
                            return board.cells.getCells()[cellLocation.x()][cellLocation.y()].isRevealed();
                        }
                    });
                    buttons[i][j] = button;
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
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setFocusPainted(false);
        return button;
    }

    public JFrame getFrame() {return frame;}
}
