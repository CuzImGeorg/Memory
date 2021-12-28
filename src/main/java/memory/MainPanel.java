package memory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel  implements MouseListener {

    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;
    private  JMenuBar menuBar = new JMenuBar();
    private Logik l;
    private int difficulty = 4;

    public MainPanel(int  WINDOW_HEIGHT, int WINDOW_WIGHT){

        setLayout(null);
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIGHT = WINDOW_WIGHT;
        l = new Logik(WINDOW_HEIGHT, WINDOW_WIGHT,difficulty);

        setBounds(0,30, WINDOW_WIGHT, WINDOW_HEIGHT-30);
        setBackground(Color.darkGray);

        addMouseListener(this);
        createMenuBar();
        add(menuBar);
        updateFrame();
    }
    private void updateFrame(){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                updateUI();
            }
        }, 1, 10, TimeUnit.MILLISECONDS);
    }



    private void createMenuBar(){
        menuBar.setBackground(Color.gray);

        //TODO MENü Bar fertig mochen

        // Methode um die Menü Bar zu erstellen



        menuBar.setBounds(0,0, 1400,30);


        menuGame();
        menuDifficulty();

    }

    public void menuDifficulty() {
        // create the Change Difficulty Menu Bar
        JMenu difficultyMenu = new JMenu("Difficulty: " + difficulty);
        JMenuItem diff_4 = new JMenuItem("Difficulty: 4");
        JMenuItem diff_5 = new JMenuItem("Difficulty: 5");
        JMenuItem diff_6 = new JMenuItem("Difficulty: 6");



        difficultyMenu.add(diff_4);
        difficultyMenu.add(diff_5);
        difficultyMenu.add(diff_6);


        diff_4.setBackground(Color.darkGray);
        diff_5.setBackground(Color.darkGray);
        diff_6.setBackground(Color.darkGray);


        diff_4.setForeground(Color.white);
        diff_5.setForeground(Color.white);
        diff_6.setForeground(Color.white);


        diff_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 4;
                difficultyMenu.setText("Difficulty: " + difficulty);
            }
        });

        diff_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 5;
                difficultyMenu.setText("Difficulty: " + difficulty);
            }
        });

        diff_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 6;
                difficultyMenu.setText("Difficulty: " + difficulty);
            }
        });



        menuBar.add(difficultyMenu);
    }
    public void menuGame(){
        JMenu menu = new JMenu("Game");
        menuBar.add(menu);

        JMenuItem menuItem_restart = new JMenuItem("Restart");
        menuItem_restart.setBackground(Color.darkGray);
        menuItem_restart.setForeground(Color.white);
        menuItem_restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.setDifficulty(difficulty);
                //TODO REsrrt the game
            }
        });

        menu.add(menuItem_restart);


    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        l.renderCards(g);




    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        l.onMouseClick(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
