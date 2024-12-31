package software.ulpgc.mineSwepper.Listeners;

import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

public interface GameStateListener {
    void notifyGameRestartRequest();
    void notifyCloseGameRequest();
    void notifyCellRevealed(CellLocation cellLocation, Cell revealedCell);
}
