package software.ulpgc.mineSwepper.model;

import java.util.List;
import java.util.Random;


public class Board {
    private static Board instance;
    private final int totalMines;
    public final Matrix cells;

    private Board(Matrix matrix, int totalMines) {
        this.totalMines = totalMines;
        this.cells = matrix;
        initializeBoard();
    }

    public static Board getInstance(Matrix matrix, int totalMines){
        if (instance == null) instance = new Board(matrix, totalMines);
        return instance;
    }

    private void initializeBoard() {
        for (int i = 0;i < cells.getRows();i++) for (int j = 0; j < cells.getColumns(); j++)
            cells.getCells()[i][j] = new Cell();
        generateMines();
        calculateAdjacentMines();
    }


    private void calculateAdjacentMines() {
        for (int i = 0; i < cells.getRows(); i++)
            for (int j = 0; j < cells.getColumns(); j++)
                if (!cells.getCells()[i][j].isMine()) {
                    int minesCount = 0;

                    List<Point> points = cells.getCells()[i][j].getAdjacentCell(new Point(i, j));
                    for (Point point : points)
                        if (point.getX() >= 0 && point.getX() < cells.getRows() && point.getY() >= 0
                                && point.getY() < cells.getColumns()
                                && cells.getCells()[point.getX()][point.getY()].isMine()) minesCount++;

                    cells.getCells()[i][j].setAdjacentMine(minesCount);
                }
    }

    private void generateMines() {
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < totalMines){
            int randomRow = rand.nextInt(cells.getRows());
            int randomCol = rand.nextInt(cells.getColumns());
            if (!cells.getCells()[randomRow][randomCol].isMine()){
                cells.getCells()[randomRow][randomCol].placeMine();
                placedMines++;
            }
        }
    }

}
