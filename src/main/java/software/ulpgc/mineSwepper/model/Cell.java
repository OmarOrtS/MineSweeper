package software.ulpgc.mineSwepper.model;

import java.util.ArrayList;
import java.util.Arrays;
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
        Arrays.stream(directions).forEach(dx -> Arrays.stream(directions)
                .mapToObj(dy -> new CellLocation(cellLocation.x() + dx, cellLocation.y() + dy))
                .forEach(cellLocations::add));
        return cellLocations;
    }

}
