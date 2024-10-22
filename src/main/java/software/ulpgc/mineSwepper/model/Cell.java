package software.ulpgc.mineSwepper.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private boolean isMine;
    private boolean isRevealed;
    private int adjacentMines;

    public Cell() {
        this.isMine = false;
        this.isRevealed = false;
        this.adjacentMines = 0;
    }

    public void placeMine() {
        this.isMine = true;
    }

    public boolean isMine() { return isMine; }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal(){
        this.isRevealed = true;
    }

    public int getAdjacentMine() {return adjacentMines;}

    public void setAdjacentMine(int adjacentMines) {this.adjacentMines = adjacentMines;}

    public List<Point> getAdjacentCell(Point point) {
        List<Point> points = new ArrayList<>();
        int[] directions = {-1, 0, 1};
        for (int dx : directions) for (int dy : directions)
            points.add(new Point(point.getX() + dx, point.getY() + dy));
        return points;
    }

    public void revealCell(Point point, Matrix cells){
        if (!cells.getCells()[point.getX()][point.getY()].isRevealed()){
            cells.getCells()[point.getX()][point.getY()].reveal();
            if (cells.getCells()[point.getX()][point.getY()].getAdjacentMine() == 0)
                revealAdjacentCell(point, cells);
        }
    }

    private void revealAdjacentCell(Point point, Matrix cells) {
        List<Point> points = cells.getCells()[point.getX()][point.getY()].getAdjacentCell(new Point(point.getX(), point.getY()));
        for (Point adjacentpoint : points)
            if (adjacentpoint.getX() >= 0 && adjacentpoint.getX() < cells.getRows() && adjacentpoint.getY() >= 0
                    && adjacentpoint.getY() < cells.getColumns()
                    && !cells.getCells()[adjacentpoint.getX()][adjacentpoint.getY()].isRevealed())
                revealCell(adjacentpoint, cells);
    }
}
