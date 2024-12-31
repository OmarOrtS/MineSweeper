package software.ulpgc.mineSwepper.Listeners;

import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

public interface GameViewListener {
    void notifyButtonToReveal(CellLocation cellLocation, Cell cell);
}
