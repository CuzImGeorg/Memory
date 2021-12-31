package memory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class WinFrame extends JFrame {




    public WinFrame(Logik l) {

        setSize(600,340);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        WinPanel wp = new WinPanel(l);
        setContentPane(wp);
        add(wp.getP2());

        setVisible(true);




    }




}
