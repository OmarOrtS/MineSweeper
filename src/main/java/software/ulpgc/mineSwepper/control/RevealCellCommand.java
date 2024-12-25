package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.BoardController;

public class RevealCellCommand implements Command {
    private final BoardController controller;
    private final CellLocation cellLocation;

    public RevealCellCommand(BoardController controller, CellLocation cellLocation) {
        this.controller = controller;
        this.cellLocation = cellLocation;
    }

    @Override
    public void execute() {controller.revealCell(cellLocation);}

}