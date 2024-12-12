package software.ulpgc.mineSwepper.Factories;

import software.ulpgc.mineSwepper.Builders.CommandBuilder;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.BoardController;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class CommandFactory {
    public static Map<String, Command> createCommands(Board board, JButton[][] buttons) {
        Map<String, Command> commands = new HashMap<>();
        BoardController controller = new BoardController(board, buttons);
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    Command command = new CommandBuilder(controller, new CellLocation(i, j)).build();
                    commands.put("Reveal:" + i + ":" + j, command);
                }));
        return commands;
    }

}
