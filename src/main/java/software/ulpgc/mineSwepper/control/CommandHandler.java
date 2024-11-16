package software.ulpgc.mineSwepper.control;

public interface CommandHandler {
    void handle(String action);
    void registerCommand(String key, Command value);
}
