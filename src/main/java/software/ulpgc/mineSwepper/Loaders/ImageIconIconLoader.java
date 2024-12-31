package software.ulpgc.mineSwepper.Loaders;

import software.ulpgc.mineSwepper.model.Dimension;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ImageIconIconLoader implements IconLoader {
    private final String path;
    private final Dimension dimension;

    public ImageIconIconLoader(String path, Dimension dimension) {
        this.path = path;
        this.dimension = dimension;
    }

    @Override
    public ImageIcon load() {return new ImageIcon(scaleImageToFitCell());}

    private Image scaleImageToFitCell() {
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(path)));
        return icon.getImage().getScaledInstance(dimension.width(), dimension.height(), Image.SCALE_SMOOTH);
    }

}