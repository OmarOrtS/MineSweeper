package software.ulpgc.mineSwepper.Factories;

import software.ulpgc.mineSwepper.Builders.CalculateAdjacentMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.GenerateMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.RevealCellCommandBuilder;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.BoardController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MineSweeperGUICommandFactory implements Factory{
    private final Board board;
    private final JButton[][] buttons;
    private final Map<String, Command> commands = new HashMap<>();

    private MineSweeperGUICommandFactory(Board board, JButton[][] buttons) {
        this.board = board;
        this.buttons = buttons;
    }

    public static Factory CreateCommandFactoryWith(Board board, JButton[][] buttons) {return new MineSweeperGUICommandFactory(board,buttons);}

    @Override
    public Map<String, Command> factorize() {return createCommands(board, buttons);}

    private Map<String, Command> createCommands(Board board, JButton[][] buttons) {
        factorizeGenerateMinesCommands(board, commands);
        factorizeCalculateAdjacentMinesCommands(board,commands);
        factorizeRevealCellCommands(board, buttons, commands);
        return commands;
    }

    private static void factorizeCalculateAdjacentMinesCommands(Board board, Map<String, Command> commands) {
        commands.put("CalculateAdjacentMines", new CalculateAdjacentMinesCommandBuilder(board).build());
    }

    private static void factorizeGenerateMinesCommands(Board board, Map<String, Command> commands) {
        commands.put("GenerateMines", new GenerateMinesCommandBuilder(board).build());
    }

    private static void factorizeRevealCellCommands(Board board, JButton[][] buttons, Map<String, Command> commands) {
        BoardController controller = new BoardController(board, buttons);
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns())
                        .forEach(j -> commands.put("Reveal:" + i + ":" + j,
                        new RevealCellCommandBuilder(controller, new CellLocation(i, j)).build())));
    }
}
