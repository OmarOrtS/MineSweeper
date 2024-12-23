package software.ulpgc.mineSwepper.Factories;

import software.ulpgc.mineSwepper.control.Command;

import java.util.Map;

public interface Factory {
    public Map<String, Command> factorize();
}
