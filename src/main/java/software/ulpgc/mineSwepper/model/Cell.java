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

    public boolean isRevealed() {
        return isRevealed;
    }

    public Cell reveal(){ return isRevealed ? this : new Cell(isMine, true, adjacentMines); }

    public int getAdjacentMine() {return adjacentMines;}

    public List<Point> getAdjacentCell(Point point) {
        List<Point> points = new ArrayList<>();
        int[] directions = {-1, 0, 1};
        for (int dx : directions) for (int dy : directions)
            points.add(new Point(point.x() + dx, point.y() + dy));
        return points;
    }

    public void revealCell(Point point, Matrix cells){
        if (!cells.getCells()[point.x()][point.y()].isRevealed())
            cells.getCells()[point.x()][point.y()].reveal();
    }

}
