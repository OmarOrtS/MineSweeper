package software.ulpgc.mineSwepper.Builders;

import software.ulpgc.mineSwepper.control.CheckWinConditionCommand;
import software.ulpgc.mineSwepper.control.Command;
import software.ulpgc.mineSwepper.view.BoardController;

public class CheckWinConditionCommandBuilder implements Builder{
    private final BoardController controller;

    public CheckWinConditionCommandBuilder(BoardController controller) {this.controller = controller;}

    @Override
    public Command build() {return new CheckWinConditionCommand(controller);}
}
