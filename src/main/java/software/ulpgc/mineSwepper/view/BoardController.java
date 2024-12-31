package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameControlListener;
import software.ulpgc.mineSwepper.Listeners.GameMasterListener;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;

public class BoardController implements GameControlListener {
    private final BoardLogic boardLogic;
    private final BoardViewUpdater boardViewUpdater;
    private final GameDialogDisplay gameDialogDisplay;

    public BoardController(Board board, JButton[][] buttons, GameMasterListener gameMasterListener) {
        GameStateController gameStateController = new GameStateController(gameMasterListener, new BoardViewUpdater(buttons, this));
        this.boardLogic = new BoardLogic(board, gameStateController, this);
        gameDialogDisplay = new GameDialogDisplay(gameStateController);
        boardViewUpdater = new BoardViewUpdater(buttons, this);
    }

    public void loadIconsForButtons(JButton[][] buttons) {boardViewUpdater.getResourceLoader().loadIconsForButtons(buttons);}

    public void revealCell(CellLocation cellLocation) {boardLogic.revealCell(cellLocation);}

    public void placeFlag(JButton button) {boardViewUpdater.placeFlag(button);}

    private boolean cellIsNotFlagged(CellLocation cellLocation) {return boardViewUpdater.cellIsNotFlagged(cellLocation);}

    public GameDialogDisplay display() {return gameDialogDisplay;}

    public void adjustIconsToNewScreenSize() {boardViewUpdater.adjustIconsToNewScreenSize();}

    public void adjustButtonsFont() {boardViewUpdater.adjustButtonsFont();}

    @Override
    public GameDialogDisplay askForDialogField() {return display();}

    @Override
    public boolean isCellMine(CellLocation cellLocation) {return boardLogic.cellIsMine(cellLocation);}

    @Override
    public boolean isCellNotRevealed(CellLocation cellLocation) {return boardLogic.cellIsNotRevealed(cellLocation);}

    @Override
    public boolean isCellNotFlagged(CellLocation cellLocation) {return cellIsNotFlagged(cellLocation);}

    public Board getBoard() {return boardLogic.getBoard();}
}
