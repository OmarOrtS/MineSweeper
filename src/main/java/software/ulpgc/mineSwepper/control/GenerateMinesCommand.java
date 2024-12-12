package software.ulpgc.mineSwepper.control;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;

import java.util.Random;

public class GenerateMinesCommand implements Command{
    private final Board board;
    private boolean minesGenerated = false;

    public GenerateMinesCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        generateMines();
    }

    private void generateMines() {
        if (minesGenerated) return;
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < board.totalMines()){
            int randomRow = rand.nextInt(board.cells.getRows());
            int randomCol = rand.nextInt(board.cells.getColumns());
            if (board.cellIsNotMine(randomRow, randomCol)){
                board.cells.getCells()[randomRow][randomCol] = new Cell(true, false, 0);
                placedMines++;
            }
        }
        minesGenerated = true;
    }
}
