package memory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;
    private  JMenuBar menuBar = new JMenuBar();

    public MainPanel(int  WINDOW_HEIGHT, int WINDOW_WIGHT){
        setLayout(null);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIGHT = WINDOW_WIGHT;

        setBounds(0,30, WINDOW_WIGHT, WINDOW_HEIGHT-30);
        setBackground(Color.darkGray);








        createMenuBar();
        add(menuBar);
    }

    private void createMenuBar(){
        menuBar.setBackground(Color.gray);

        //TODO MENü Bar fertig mochen

        // Methode um die Menü Bar zu erstellen
        JMenu menuFile = new JMenu("Datei");

        menuBar.add(menuFile);
        menuBar.setBounds(0,0, 1400,30);
        JMenuItem menuItemFileNewText = new JMenuItem("Text");

        menuBar.add(menuFile);
        menuBar.setBounds(0,0, 1400,30);


        menuFile.add(menuItemFileNewText);

    }
}
