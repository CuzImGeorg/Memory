package memory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class WinFrame extends JFrame {

    private   WinPanel wp;


    public WinFrame(Logik l) {
        super("Scores");
        wp  = new WinPanel(l);
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
