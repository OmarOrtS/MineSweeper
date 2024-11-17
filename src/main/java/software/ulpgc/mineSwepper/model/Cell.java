package software.ulpgc.mineSwepper.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final boolean isMine;
    private final boolean isRevealed;
    private final int adjacentMines;

    public Cell(boolean isMine, boolean isRevealed, int adjacentMines) {
        this.isMine = isMine;
        this.isRevealed = isRevealed;
        this.adjacentMines = adjacentMines;
    }

    public boolean isMine() { return isMine; }

    public boolean isRevealed() { return isRevealed; }

    public Cell reveal(){ return isRevealed ? this : new Cell(isMine, true, adjacentMines); }

    public int getAdjacentMine() {return adjacentMines;}

    public List<CellLocation> getAdjacentCell(CellLocation cellLocation) {
        List<CellLocation> cellLocations = new ArrayList<>();
        int[] directions = {-1, 0, 1};
        for (int dx : directions) for (int dy : directions)
            cellLocations.add(new CellLocation(cellLocation.x() + dx, cellLocation.y() + dy));
        return cellLocations;
    }

    public void revealCell(CellLocation cellLocation, Matrix cells){
        if (!cells.getCells()[cellLocation.x()][cellLocation.y()].isRevealed())
            cells.getCells()[cellLocation.x()][cellLocation.y()].reveal();
    }

}
