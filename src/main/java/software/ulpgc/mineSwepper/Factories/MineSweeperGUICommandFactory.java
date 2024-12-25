package software.ulpgc.mineSwepper.Factories;

import software.ulpgc.mineSwepper.Builders.CalculateAdjacentMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.CheckWinConditionCommandBuilder;
import software.ulpgc.mineSwepper.Builders.GenerateMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.RevealCellCommandBuilder;
import software.ulpgc.mineSwepper.Listeners.ResetGameListener;
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
    private final Map<String, Command> commands = new HashMap<>();
    private final BoardController controller;

    private MineSweeperGUICommandFactory(Board board, JButton[][] buttons, ResetGameListener resetGameListener) {
        this.board = board;
        this.controller = new BoardController(board, buttons, resetGameListener);
    }

    public static Factory CreateCommandFactoryWith(Board board, JButton[][] buttons, ResetGameListener resetGameListener) {
        return new MineSweeperGUICommandFactory(board,buttons, resetGameListener);}

    @Override
    public Map<String, Command> factorize() {return createCommands();}

    @Override
    public void createCommandOnDemand(Object object) {factorizeGenerateMinesCommands(board, commands, (CellLocation) object);}

    private Map<String, Command> createCommands() {
        factorizeCalculateAdjacentMinesCommands();
        factorizeRevealCellCommands();
        factorizeCheckWinConditionCommands();
        return commands;
    }

    private void factorizeCheckWinConditionCommands() {
        commands.put("CheckWinCondition", new CheckWinConditionCommandBuilder(controller).build());
    }

    private void factorizeCalculateAdjacentMinesCommands() {
        commands.put("CalculateAdjacentMines", new CalculateAdjacentMinesCommandBuilder(board).build());
    }

    private static void factorizeGenerateMinesCommands(Board board, Map<String, Command> commands, CellLocation excludedCell) {
        commands.put("GenerateMines", new GenerateMinesCommandBuilder(board, excludedCell).build());
    }

    private  void factorizeRevealCellCommands() {
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns())
                        .forEach(j -> commands.put("Reveal:" + i + ":" + j,
                        new RevealCellCommandBuilder(controller, new CellLocation(i, j)).build())));
    }
}
