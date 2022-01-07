package memory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WinPanel extends JPanel {

    private JPanel p2 = new JPanel();
    private final Logik l;

    public WinPanel(Logik l, boolean won){
        this.l = l;
        p2.setBounds(200,0,385,300);
        p2.setLayout(null);
        p2.setBackground(Color.darkGray);
        p2.setBorder(new LineBorder(Color.white, 2));

        generateModes();
        l.loadScores(l.getDifficulty());
        for(JLabel label : modes) {
            if(label.getText().substring(12).equalsIgnoreCase(l.getDifficultyAsString())){
                label.setBorder(new LineBorder(Color.WHITE, 2));
            }
        }
        generateScores();
        loadScores();

        if(won) {
            ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
            ses.schedule(this::nameInput, 100, TimeUnit.MILLISECONDS);
        }

    }

    private ArrayList<JLabel> modes = new ArrayList<>();

    public void generateModes(){
        setBounds(0,0, 200,340);
        setLayout(null);
        setBackground(Color.BLACK);
        modes.add(new JLabel("Difficulty: Easy"));
        modes.add(new JLabel("Difficulty: Medium"));
        modes.add(new JLabel("Difficulty: Hard"));
        for (JLabel mode : modes) {
            mode.setBounds(0,modes.indexOf(mode)*100,200,100);
            mode.setBackground(Color.DARK_GRAY);
            mode.setForeground(Color.white);
            mode.setBorder(new LineBorder(Color.BLACK, 2));
            mode.setOpaque(true);
            mode.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    l.loadScores(modes.indexOf(mode) +4);
                    for (JLabel l : modes) {
                        l.setBorder(new LineBorder(Color.black, 2));
                    }
                    mode.setBorder(new LineBorder(Color.white, 2));
                    p2.removeAll();
                    generateScores();
                    loadScores();
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
            });
            add(mode);
        }
    }
    private ArrayList<JLabel> labels = new ArrayList<>();
    public void generateScores(){
        labels.clear();
        int counter = 0;
        int counter2 = 0;
        for (String s : l.getScores()){
            JLabel scores = new JLabel();
            scores.setText(s);
            scores.setBounds(counter2*200+5,counter*15+4, 200,15);
            scores.setBackground(Color.darkGray);
            scores.setForeground(Color.white);
            scores.setOpaque(true);
            labels.add(scores);
            if(counter2 == 1 && counter == 18) return;
            if(counter == 18){
                counter = 0;
                counter2++;
            }else {
                counter++;
            }
        }
    }
    private JFrame nameSystem;
    public void nameInput(){

       nameSystem  = new JFrame("YOU WON ");

        nameSystem.setSize(500,200);
        nameSystem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nameSystem.setLayout(null);
        nameSystem.setLocationRelativeTo(null);


        JPanel p = new JPanel();
        p.setBounds(0,0,500,200);
        p.setLayout(null);
        p.setBackground(Color.darkGray);
        p.setBorder(new LineBorder(Color.white, 2));
        nameSystem.setContentPane(p);

        JLabel won = new JLabel();
        won.setBounds(10,10,400,30);
        won.setBackground(Color.darkGray);
        won.setForeground(Color.white);
        won.setText("YOU HAVE WON THE GAME IN " + l.getTimeAsString()+ " Minuets " + " in " + l.getCounterZuege() + " tries" +" with " + l.getScore() + " Points");
        p.add(won);

        JLabel entername = new JLabel();
        entername.setBounds(10,40,400,30);
        entername.setBackground(Color.darkGray);
        entername.setForeground(Color.white);
        entername.setText("PLS ENTER NAME");
        p.add(entername);

        JTextArea name = new JTextArea();
        name.setBounds(10,80,150,20);
        name.setBackground(Color.darkGray);
        name.setForeground(Color.white);
        name.setBorder(new LineBorder(Color.black,2));
        p.add(name);

        JButton submit = new JButton("Submit");
        submit.setBounds(10,110,60,40);
        submit.setBackground(Color.green);
        submit.setForeground(Color.black);
        submit.setBorder(new LineBorder(Color.black,2));
        submit.addActionListener(e -> {
            nameSystem.dispose();
            l.checkIfnameIsValidAndSafeScore(name.getText());
        });
        p.add(submit);
        nameSystem.setVisible(true);
    }

    public void loadScores(){
        for(JLabel l: labels) {
            p2.add(l);
        }
        updateUI();
    }

    public JFrame getNameSystem() {
        return nameSystem;
    }

    public JPanel getP2() {
        return p2;
    }
}
