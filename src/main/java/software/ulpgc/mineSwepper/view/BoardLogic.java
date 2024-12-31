package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameControlListener;
import software.ulpgc.mineSwepper.Listeners.GameStateListener;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

import java.util.*;

public class BoardLogic {
    private final Board board;
    private final GameStateListener gameStateListener;
    private final GameControlListener gameControlListener;

    public BoardLogic(Board board, GameStateListener gameStateListener, GameControlListener gameControlListener) {
        this.board = board;
        this.gameStateListener = gameStateListener;
        this.gameControlListener = gameControlListener;
    }

    public void revealCell(CellLocation cellLocation) {
        Cell cell = board.cells.getCells()[cellLocation.x()][cellLocation.y()];

        if (cell.isRevealed() || !gameControlListener.isCellNotFlagged(cellLocation)) return;

        Cell revealedCell = cell.reveal();
        board.cells.getCells()[cellLocation.x()][cellLocation.y()] = revealedCell;
        gameStateListener.notifyCellRevealed(cellLocation, revealedCell);
        if (revealedCell.isMine()) return;
        if (revealedCell.getAdjacentMine() == 0) propagateReveal(cellLocation);
        }

    public void propagateReveal(CellLocation cellLocation) {
        int[] directions = {-1, 0, 1};
        Arrays.stream(directions).forEach(x -> Arrays.stream(directions).forEach(y -> {
            if (x == 0 && y == 0) return;
            if (cellIsValidToReval(cellLocation, x, y))
                revealCell(nextCell(cellLocation, x, y));
        }));
    }

    private boolean cellIsValidToReval(CellLocation cellLocation, int x, int y) {
        return isCellWithinBounds(nextCell(cellLocation, x, y))
                && cellIsNotRevealed(nextCell(cellLocation, x, y))
                && gameControlListener.isCellNotFlagged(nextCell(cellLocation, x, y));
    }

    private static CellLocation nextCell(CellLocation cellLocation, int x, int y) {
        return new CellLocation(cellLocation.x() + x, cellLocation.y() + y);
    }

    public boolean cellIsNotRevealed(CellLocation cellLocation) {return !board.cells.getCells()[cellLocation.x()][cellLocation.y()].isRevealed(); }

    public boolean isCellWithinBounds(CellLocation cellLocation) {
        return cellLocation.x() >= 0 && cellLocation.x() < board.cells.getRows() && cellLocation.y() >= 0 && cellLocation.y() < board.cells.getColumns();
    }

    public boolean cellIsMine(CellLocation cellLocation) {
        return board.cells.getCells()[cellLocation.x()][cellLocation.y()].isMine();
    }

    public Board getBoard() {
        return board;
    }
}
