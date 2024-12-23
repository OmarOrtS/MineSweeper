package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

import java.util.stream.IntStream;

public class CalculateAdjacentMinesCommand implements Command{
    private final Board board;
    private boolean adjacentMinesCalculated = false;

    public CalculateAdjacentMinesCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        if (adjacentMinesCalculated) return;
        calculateAdjacentMines();
        adjacentMinesCalculated = true;
    }

    private void calculateAdjacentMines() {
        IntStream.range(0, board.cells.getRows()).forEach(i ->
                IntStream.range(0, board.cells.getColumns()).forEach(j -> {
                    if (board.cellIsNotMine(i, j)) {
                        long minesCount = board.cells.getCells()[i][j]
                                .getAdjacentCell(new CellLocation(i, j)).stream()
                                .filter(l -> board.cellIsWithinLimits(l) &&
                                        !board.cellIsNotMine(l.x(), l.y()))
                                .count();

                        board.cells.getCells()[i][j] = new Cell(false, false, (int) minesCount);
                    }
                })
        );
    }
}
