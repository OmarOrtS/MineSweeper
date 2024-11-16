package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.Point;
import software.ulpgc.mineSwepper.view.BoardController;

public class RevealCellCommand implements Command {
    private final BoardController controller;
    private final Point point;

    public RevealCellCommand(BoardController controller, Point point) {
        this.controller = controller;
        this.point = point;
    }

    @Override
    public void execute() {
        controller.revealCell(point);
    }

}