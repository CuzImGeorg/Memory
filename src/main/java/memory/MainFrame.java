package memory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final int WINDOW_HEIGHT = 1430,
                      WINDOW_WIGHT = 1400;



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
}
