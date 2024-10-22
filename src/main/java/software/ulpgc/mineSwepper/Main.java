package software.ulpgc.mineSwepper;

import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.Matrix;

public class Main {
    public static void main(String[] args) {
        Board board = Board.getInstance(Matrix.getInstance(16,16), 40);
    }
}
