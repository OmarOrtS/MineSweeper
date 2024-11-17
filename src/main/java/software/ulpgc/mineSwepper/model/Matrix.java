package software.ulpgc.mineSwepper.model;

public class Matrix {
    private static Matrix instance;
    protected final Cell[][] cells;
    private final int rows;
    private final int columns;

    private Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = new Cell[rows][columns];
    }

    public static Matrix getInstance(int rows, int columns){
        if (instance == null) instance = new Matrix(rows,columns);
        return instance;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() { return rows; }

    public int getColumns() {
        return columns;
    }
}
