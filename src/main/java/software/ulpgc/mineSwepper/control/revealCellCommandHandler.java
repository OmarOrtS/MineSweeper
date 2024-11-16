package software.ulpgc.mineSwepper.control;

import java.util.HashMap;
import java.util.Map;

public class revealCellCommandHandler implements CommandHandler{
    private final Map<String, Command> commands;

    public revealCellCommandHandler() {
        commands = new HashMap<>();
    }

    @Override
    public void registerCommand(String key, Command value) { commands.put(key, value); }

    @Override
    public void handle(String action) {
        if(commands.get(action) != null) commands.get(action).execute();
    }
}
