package software.ulpgc.mineSwepper.Builders;


import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.control.GenerateMinesCommand;
import software.ulpgc.mineSwepper.model.Board;
import software.ulpgc.mineSwepper.model.CellLocation;

public class GenerateMinesCommandBuilder implements Builder {
    private final Board board;
    private final CellLocation excludedCell;

    public GenerateMinesCommandBuilder(Board board, CellLocation excludedCell) {
        this.board = board;
        this.excludedCell = excludedCell;
    }

    @Override
    public Command build() {return new GenerateMinesCommand(board,excludedCell);}
}
