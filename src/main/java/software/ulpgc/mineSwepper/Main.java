package software.ulpgc.mineSwepper;

import software.ulpgc.mineSwepper.control.RevealCellCommand;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Matrix;
import software.ulpgc.mineSwepper.view.MineSweeperGUI;

public class Main {
    public static void main(String[] args) {
        MineSweeperGUI mineSweeperGUI = new MineSweeperGUI(Board.getInstance(Matrix.getInstance(16,16), 40));
        mineSweeperGUI.put("RevealCell", new RevealCellCommand(mineSweeperGUI.getBoard(), mineSweeperGUI.getButtons()));
    }
}
