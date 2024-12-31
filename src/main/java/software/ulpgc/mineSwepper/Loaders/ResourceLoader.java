package software.ulpgc.mineSwepper.Loaders;

import software.ulpgc.mineSwepper.model.Dimension;

import javax.swing.*;

public class ResourceLoader {
    private ImageIcon mineIcon;
    private ImageIcon flagIcon;

    public void loadIconsForButtons(JButton[][] buttons) {
        this.mineIcon = new ImageIconIconLoader("/Minesweeper_mine.png", new Dimension(getWidth(buttons),getHeight(buttons))).load();
        this.flagIcon = new ImageIconIconLoader("/Minesweeper_flag.png", new Dimension(getWidth(buttons),getHeight(buttons))).load();
    }

    private static int getHeight(JButton[][] buttons) {return buttons[0][0].getHeight();}

    private static int getWidth(JButton[][] buttons) {return buttons[0][0].getWidth();}

    public ImageIcon getMineIcon() {return mineIcon;}

    public ImageIcon getFlagIcon() {return flagIcon;}
}
