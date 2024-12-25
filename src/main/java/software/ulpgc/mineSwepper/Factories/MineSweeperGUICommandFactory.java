package software.ulpgc.mineSwepper.Factories;

import software.ulpgc.mineSwepper.Builders.CalculateAdjacentMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.CheckWinConditionCommandBuilder;
import software.ulpgc.mineSwepper.Builders.GenerateMinesCommandBuilder;
import software.ulpgc.mineSwepper.Builders.RevealCellCommandBuilder;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.BoardController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class MineSweeperGUICommandFactory implements Factory{
    private final Map<String, Command> commands = new HashMap<>();
    private final BoardController controller;

    private MineSweeperGUICommandFactory(BoardController controller) {
        this.controller = controller;
    }

    public static Factory CreateCommandFactoryWith(BoardController controller) {
        return new MineSweeperGUICommandFactory(controller);}

    @Override
    public Map<String, Command> factorize() {return createCommands();}

    @Override
    public void createCommandOnDemand(Object object) {factorizeGenerateMinesCommands(controller.getBoard(), commands, (CellLocation) object);}

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
        commands.put("CalculateAdjacentMines", new CalculateAdjacentMinesCommandBuilder(controller.getBoard()).build());
    }

    private static void factorizeGenerateMinesCommands(Board board, Map<String, Command> commands, CellLocation excludedCell) {
        commands.put("GenerateMines", new GenerateMinesCommandBuilder(board, excludedCell).build());
    }

    private  void factorizeRevealCellCommands() {
        IntStream.range(0, controller.getBoard().cells.getRows()).forEach(i ->
                IntStream.range(0, controller.getBoard().cells.getColumns())
                        .forEach(j -> commands.put("Reveal:" + i + ":" + j,
                        new RevealCellCommandBuilder(controller, new CellLocation(i, j)).build())));
    }
}
