package software.ulpgc.mineSwepper.Builders;

import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.control.RevealCellCommand;
import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.BoardController;

public class CommandBuilder implements Builder {
    private final BoardController controller;
    private final CellLocation cellLocation;

    public CommandBuilder(BoardController controller, CellLocation cellLocation) {
        this.controller = controller;
        this.cellLocation = cellLocation;
    }

    @Override
    public Command build() {
        return new RevealCellCommand(controller, cellLocation);
    }
}
