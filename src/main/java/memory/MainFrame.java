package memory;

import javax.swing.*;


public class MainFrame extends JFrame {

    private final int   WINDOW_HEIGHT= 1000,
            WINDOW_WIGHT   = (int) (WINDOW_HEIGHT*1.5 + 30);

    private MainPanel mainPanel;

    public MainFrame(){
        setLayout(null);
        setTitle("Memory");
        setSize(WINDOW_WIGHT,WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new MainPanel(WINDOW_HEIGHT, WINDOW_WIGHT);
        setContentPane(mainPanel);
        setVisible(true);
    }

    public int getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public int getWINDOW_WIGHT() {
        return WINDOW_WIGHT;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }
}
