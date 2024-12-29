package software.ulpgc.mineSwepper.Loaders;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImageIconIconLoader implements IconLoader {
    private final String path;

    public ImageIconIconLoader( String path) {
        this.path = path;
    }

    @Override
    public ImageIcon load() {return new ImageIcon(scaleImageToFitCell());}

    private Image scaleImageToFitCell() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        return icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
    }

}