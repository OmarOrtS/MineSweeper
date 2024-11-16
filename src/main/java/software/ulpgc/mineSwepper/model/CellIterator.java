package software.ulpgc.mineSwepper.model;

import java.util.Iterator;

public class CellIterator implements Iterator<Cell> {
    private final Matrix matrix;
    private int currentRow = 0;
    private int currentColumn = 0;

    public CellIterator(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public boolean hasNext() {
        return currentColumn + 1 < matrix.getRows() &&
                currentColumn + 1 < matrix.getColumns();
    }

    @Override
    public Cell next() {
        Cell currentCell = matrix.cells[currentRow][currentColumn];
        currentColumn++;
        if (isLastColumn()) {
            currentColumn = 0;
            currentRow++;
        }
        return currentCell;
    }

    private boolean isLastColumn() {
        return currentColumn == matrix.getColumns();
    }
}
