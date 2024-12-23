package software.ulpgc.mineSwepper.Builders;

import software.ulpgc.mineSwepper.control.CalculateAdjacentMinesCommand;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.model.Board;

public class CalculateAdjacentMinesCommandBuilder implements Builder{
    private final Board board;

    public CalculateAdjacentMinesCommandBuilder(Board board) {
        this.board = board;
    }

    @Override
    public Command build() {return new CalculateAdjacentMinesCommand(board);}
}
