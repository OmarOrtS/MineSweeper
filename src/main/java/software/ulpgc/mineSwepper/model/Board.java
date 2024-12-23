package software.ulpgc.mineSwepper.model;

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
    }

    public boolean cellIsWithinLimits(CellLocation l) {
        return l.x() >= 0 && l.x() < cells.getRows() &&
                l.y() >= 0 && l.y() < cells.getColumns();
    }

    public boolean cellIsNotMine(int i, int j) {
        return !cells.getCells()[i][j].isMine();
    }

    public int totalMines() {
        return totalMines;
    }
}
