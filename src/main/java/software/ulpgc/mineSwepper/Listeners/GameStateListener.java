package software.ulpgc.mineSwepper.Listeners;

public interface GameStateListener {
    void notifyGameRestartRequest();
    void notifyCloseGameRequest();
}
