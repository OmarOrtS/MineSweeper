package software.ulpgc.mineSwepper.model;

public class Matrix {
    protected final Cell[][] cells;
    private final int rows;
    private final int columns;

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = new Cell[rows][columns];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() { return rows; }

    public int getColumns() {
        return columns;
    }
}
