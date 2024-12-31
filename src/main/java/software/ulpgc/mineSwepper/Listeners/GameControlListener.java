package software.ulpgc.mineSwepper.Listeners;

import software.ulpgc.mineSwepper.model.CellLocation;
import software.ulpgc.mineSwepper.view.GameDialogDisplay;

public interface GameControlListener {
    GameDialogDisplay askForDialogField();
    boolean isCellMine(CellLocation cellLocation);
    boolean isCellNotRevealed(CellLocation cellLocation);
    boolean isCellNotFlagged(CellLocation cellLocation);
}
