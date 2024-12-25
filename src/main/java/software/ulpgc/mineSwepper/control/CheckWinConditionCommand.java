package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.view.BoardController;

import java.util.Arrays;

public class CheckWinConditionCommand implements Command{
    private final BoardController controller;

    public CheckWinConditionCommand(BoardController controller) {this.controller = controller;}

    @Override
    public void execute() {
        checkWinCondition();
    }

    private void checkWinCondition() {
        long revealedCells = getRevealedCells();
        int totalCells = controller.getBoard().cells.getRows() * controller.getBoard().cells.getColumns();
        if (revealedCells == totalCells - controller.getBoard().totalMines()) {
            controller.display().showEndGameDialog("Congratulations! You won!");
        }
    }

    private long getRevealedCells() {
        return Arrays.stream(controller.getBoard().cells.getCells())
                .flatMap(Arrays::stream)
                .filter(c -> !c.isMine())
                .filter(Cell::isRevealed)
                .count();
    }
}
