package software.ulpgc.mineSwepper.model;

import java.util.Random;
import java.util.stream.IntStream;


public class Board {
    private final int totalMines;
    public final Matrix cells;

    private Board(Matrix matrix, int totalMines) {
        this.totalMines = totalMines;
        this.cells = matrix;
        initializeBoard();
    }

    public static Board createBoard(Matrix matrix, int totalMines){
        return new Board(matrix, totalMines);
    }

    private void initializeBoard() {
        for (int i = 0;i < cells.getRows();i++) for (int j = 0; j < cells.getColumns(); j++)
            cells.getCells()[i][j] = new Cell(false, false, 0);
        generateMines();
        calculateAdjacentMines();
    }


    private void calculateAdjacentMines() {
        IntStream.range(0, cells.getRows()).forEach(i ->
                IntStream.range(0, cells.getColumns()).forEach(j -> {
                    if (cellIsNotMine(i, j)) {
                        long minesCount = cells.getCells()[i][j]
                                .getAdjacentCell(new CellLocation(i, j)).stream()
                                .filter(l -> cellIsWithinLimits(l) &&
                                        !cellIsNotMine(l.x(), l.y()))
                                .count();

                        cells.getCells()[i][j] = new Cell(false, false, (int) minesCount);
                    }
                })
        );
    }

    private boolean cellIsWithinLimits(CellLocation l) {
        return l.x() >= 0 && l.x() < cells.getRows() &&
                l.y() >= 0 && l.y() < cells.getColumns();
    }

    private boolean cellIsNotMine(int i, int j) {
        return !cells.getCells()[i][j].isMine();
    }

    private void generateMines() {
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < totalMines){
            int randomRow = rand.nextInt(cells.getRows());
            int randomCol = rand.nextInt(cells.getColumns());
            if (cellIsNotMine(randomRow, randomCol)){
                cells.getCells()[randomRow][randomCol] = new Cell(true, false, 0);
                placedMines++;
            }
        }
    }

    public int totalMines() {
        return totalMines;
    }
}
