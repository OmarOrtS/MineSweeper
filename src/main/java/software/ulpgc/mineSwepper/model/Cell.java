package software.ulpgc.mineSwepper.model;

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

}
