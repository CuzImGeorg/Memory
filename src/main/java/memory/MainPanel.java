package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainPanel extends JPanel  implements MouseListener {

    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;
    private  JMenuBar menuBar = new JMenuBar();
    private final Logik l;
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
        counterCards();
        counterZuege();
        menuScore();
        add(menuBar);
        updateFrame();
    }
    private void updateFrame(){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(this::updateUI, 1, 10, TimeUnit.MILLISECONDS);
    }

    private void createMenuBar() {
        menuBar.setBackground(Color.darkGray);
        menuBar.setBounds(0, 0, 150, 30);
        menuGame();
        time();
        menuDifficulty();
    }
    JLabel cKard = new JLabel();
    public void counterCards() {
        cKard = new JLabel();
        cKard.setText("Aufgedeckte Kartenpaare  " + l.getAufgedeckteKartenPaare() + "/" + 4*l.getDifficulty()/2);
        cKard.setBounds(1200,0,300,30);
        cKard.setBackground(Color.darkGray);
        cKard.setForeground(Color.white);
        cKard.setOpaque(true);
        add(cKard);
    }

    public void updateCounterCards() {
        cKard.setText("Aufgedeckte Kartenpaare  " + l.getAufgedeckteKartenPaare() + "/" + 4*l.getDifficulty()/2);
    }

    JLabel counterZuege = new JLabel();
    public void counterZuege(){
        counterZuege = new JLabel();
        counterZuege.setText("Zuege: " + l.getCounterZuege());
        counterZuege.setBounds(1100,0,100,30);
        counterZuege.setBackground(Color.darkGray);
        counterZuege.setForeground(Color.white);
        counterZuege.setOpaque(true);
        add(counterZuege);
    }

    public void updateCounterZuege() {
        counterZuege.setText("Zuege: " + l.getCounterZuege());
    }

    JLabel menu_score;
    public void menuScore(){
        menu_score = new JLabel("Score: 0" );
        menu_score.setBackground(Color.lightGray);
        menu_score.setForeground(Color.WHITE);

        menu_score.setBounds(750, 0, 100, 30);
        add(menu_score);
    }

    public void updateScore(){
        menu_score.setText("Score : " + l.getScore());
    }

    public void menuDifficulty() {
        JMenu difficultyMenu = new JMenu("Difficulty: " + "Easy");
        difficultyMenu.setForeground(Color.white);
        JMenuItem diff_4 = new JMenuItem("Difficulty: Easy");
        JMenuItem diff_5 = new JMenuItem("Difficulty: Medium");
        JMenuItem diff_6 = new JMenuItem("Difficulty: Hard");

        difficultyMenu.add(diff_4);
        difficultyMenu.add(diff_5);
        difficultyMenu.add(diff_6);

        diff_4.setBackground(Color.darkGray);
        diff_5.setBackground(Color.darkGray);
        diff_6.setBackground(Color.darkGray);

        diff_4.setForeground(Color.white);
        diff_5.setForeground(Color.white);
        diff_6.setForeground(Color.white);


        diff_4.addActionListener(e -> {
            difficulty = 4;
            difficultyMenu.setText("Difficulty: " + "Easy");
            l.setDifficulty(difficulty);
            l.reset();
        });

        diff_5.addActionListener(e -> {
            difficulty = 5;
            difficultyMenu.setText("Difficulty: " + "Medium");
            l.setDifficulty(difficulty);
            l.reset();
        });

        diff_6.addActionListener(e -> {
            difficulty = 6;
            difficultyMenu.setText("Difficulty: " + "Hard");
            l.setDifficulty(difficulty);
            l.reset();
        });

        menuBar.add(difficultyMenu);
    }

    public void menuGame(){
        JMenu menu = new JMenu("Game");
        menu.setForeground(Color.white);
        menuBar.add(menu);

        JMenuItem menuItem_restart = new JMenuItem("Restart");
        menuItem_restart.setBackground(Color.darkGray);
        menuItem_restart.setForeground(Color.white);
        menuItem_restart.addActionListener(e -> {
            l.setDifficulty(difficulty);
            l.reset();
        });

        JMenuItem showScores = new JMenuItem("Scores");
        showScores.setBackground(Color.darkGray);
        showScores.setForeground(Color.white);
        showScores.addActionListener(e -> {
            WinFrame f = new WinFrame(l, false);
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setBackground(Color.darkGray);
        exit.setForeground(Color.white);
        exit.addActionListener(e -> Runtime.getRuntime().exit(0));

        menu.add(showScores);
        menu.add(menuItem_restart);
        menu.add(exit);

    }

    public void time(){
        JLabel timer = new JLabel("Timer");
        timer.setBackground(Color.lightGray);
        timer.setForeground(Color.WHITE);

        timer.setBounds(WINDOW_WIGHT - 98, 0, 100, 30);
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(() -> timer.setText( "Time: " + l.getTimeAsString()), 0 , 100, TimeUnit.MILLISECONDS);
        add(timer);
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
