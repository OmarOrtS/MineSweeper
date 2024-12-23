package software.ulpgc.mineSwepper;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Matrix;
import software.ulpgc.mineSwepper.view.MineSweeperGUI;

public class Main {
    public static void main(String[] args) {
        new MineSweeperGUI(Board.createBoard(new Matrix(16, 16), 40));
    }
}
