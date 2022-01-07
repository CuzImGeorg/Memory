package memory;

import javax.swing.*;

public class WinFrame extends JFrame {

    private   WinPanel wp;

    public WinFrame(Logik l, boolean won) {
        super("Scores");
        wp  = new WinPanel(l , won);
        setSize(600,340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setContentPane(wp);
        add(wp.getP2());
        setVisible(true);
    }

    public WinPanel getWinPanel() {
        return wp;
    }
}
