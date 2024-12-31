package software.ulpgc.mineSwepper.view;

import software.ulpgc.mineSwepper.Listeners.GameControlListener;
import software.ulpgc.mineSwepper.Listeners.GameViewListener;
import software.ulpgc.mineSwepper.Loaders.ResourceLoader;
import software.ulpgc.mineSwepper.model.Cell;
import software.ulpgc.mineSwepper.model.CellLocation;

import javax.swing.*;
import java.awt.*;
import java.util.stream.IntStream;

public class BoardViewUpdater implements GameViewListener {
    private final JButton[][] buttons;
    private final GameControlListener gameControlListener;
    private final ResourceLoader resourceLoader;

    public BoardViewUpdater(JButton[][] buttons, GameControlListener gameControlListener) {
        this.buttons = buttons;
        this.gameControlListener = gameControlListener;
        this.resourceLoader = new ResourceLoader();
    }

    @Override
    public void notifyButtonToReveal(CellLocation cellLocation, Cell cell) {revealButton(cellLocation, cell);}

    private void revealButton(CellLocation cellLocation, Cell revealedCell) {
        JButton button = buttons[cellLocation.x()][cellLocation.y()];
        if (revealedCell.isMine()) {
            setMineVisuals(button);
            gameControlListener.askForDialogField().showEndGameDialog("Game Over! You hit a mine!");
        } else {
            setRevealedCellVisuals(revealedCell, button);
        }
    }

    private static void setRevealedCellVisuals(Cell revealedCell, JButton button) {
        int adjacentMines = revealedCell.getAdjacentMine();
        button.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
        button.setBackground(Color.LIGHT_GRAY);
        button.setEnabled(false);
    }

    private void setMineVisuals(JButton button) {
        if (resourceLoader.getMineIcon() == null) resourceLoader.loadIconsForButtons(buttons);
        button.setIcon(resourceLoader.getMineIcon());
        button.setText("");
        button.setBackground(Color.RED);
    }

    public void adjustIconsToNewScreenSize() {
        resourceLoader.loadIconsForButtons(buttons);
        IntStream.range(0, buttons.length).forEach(i ->
                IntStream.range(0, buttons[i].length).forEach(j ->
                        adjustButtonIcon(buttons[i][j], new CellLocation(i,j))
                )
        );
    }

    private void adjustButtonIcon(JButton button, CellLocation cellLocation) {
        if (button.getIcon() == null) return;
        button.setIcon(gameControlListener.isCellMine(cellLocation) &&
                !gameControlListener.isCellNotRevealed(cellLocation) ?
                resourceLoader.getMineIcon() : resourceLoader.getFlagIcon());
    }


    public void adjustButtonsFont() {
        IntStream.range(0, buttons.length).forEach(i ->
                IntStream.range(0, buttons[i].length).forEach(j ->
                        adjustButtonFont(buttons[i][j], new CellLocation(i,j))
                )
        );
    }

    private void adjustButtonFont(JButton button, CellLocation cellLocation) {
        if (gameControlListener.isCellNotRevealed(cellLocation)) return;
        int fontSize = Math.min(button.getWidth(), button.getHeight() / 4);
        button.setFont(new Font("Arial", Font.BOLD, fontSize));
    }

    public void placeFlag(JButton button) {button.setIcon(button.getIcon() == null ? resourceLoader.getFlagIcon() : null);}

    public boolean cellIsNotFlagged(CellLocation cellLocation) {return buttons[cellLocation.x()][cellLocation.y()].getIcon()==null;}

    public ResourceLoader getResourceLoader() {return resourceLoader;}
}
