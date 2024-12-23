package software.ulpgc.mineSwepper.Builders;


import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.control.GenerateMinesCommand;
import software.ulpgc.mineSwepper.model.Board;

public class GenerateMinesCommandBuilder implements Builder {
    private final Board board;

    public GenerateMinesCommandBuilder(Board board) {
        this.board = board;
    }

    @Override
    public Command build() {return new GenerateMinesCommand(board);}
}
