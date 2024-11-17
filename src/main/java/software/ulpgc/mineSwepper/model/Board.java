package software.ulpgc.mineSwepper.model;

import java.util.List;
import java.util.Random;


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
        for (int i = 0; i < cells.getRows(); i++)
            for (int j = 0; j < cells.getColumns(); j++)
                if (!cells.getCells()[i][j].isMine()) {
                    int minesCount = 0;

                    List<CellLocation> cellLocations = cells.getCells()[i][j].getAdjacentCell(new CellLocation(i, j));
                    for (CellLocation cellLocation : cellLocations)
                        if (cellLocation.x() >= 0 && cellLocation.x() < cells.getRows() && cellLocation.y() >= 0
                                && cellLocation.y() < cells.getColumns()
                                && cells.getCells()[cellLocation.x()][cellLocation.y()].isMine()) minesCount++;

                    cells.getCells()[i][j] = new Cell(false, false, minesCount);
                }
    }

    private void generateMines() {
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < totalMines){
            int randomRow = rand.nextInt(cells.getRows());
            int randomCol = rand.nextInt(cells.getColumns());
            if (!cells.getCells()[randomRow][randomCol].isMine()){
                cells.getCells()[randomRow][randomCol] = new Cell(true, false, 0);
                placedMines++;
            }
        }
    }

    public int totalMines() {
        return totalMines;
    }
}
