package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameMasterListener;
import software.ulpgc.mineSwepper.Listeners.GameStateListener;
import software.ulpgc.mineSwepper.Listeners.GameViewListener;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

public class GameStateController implements GameStateListener {
    private final GameMasterListener gameMasterListener;
    private final GameViewListener gameViewListener;

    public GameStateController(GameMasterListener gameMasterListener, GameViewListener gameViewListener) {
        this.gameMasterListener = gameMasterListener;
        this.gameViewListener = gameViewListener;
    }

    @Override
    public void notifyGameRestartRequest() {resetGame();}

    @Override
    public void notifyCloseGameRequest() {closeGame();}

    @Override
    public void notifyCellRevealed(CellLocation cellLocation, Cell revealedCell) {gameViewListener.notifyButtonToReveal(cellLocation, revealedCell);}

    private void closeGame() {gameMasterListener.onExit();}

    private void resetGame() {gameMasterListener.onGameReset();}
}
