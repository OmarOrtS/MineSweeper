package software.ulpgc.mineSwepper.model;

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
}
