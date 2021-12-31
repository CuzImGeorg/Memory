package memory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class WinPanel extends JPanel {

    private JPanel p2 = new JPanel();
    private final Logik l;
    private JLabel scores = new JLabel();

    public WinPanel(Logik l){
        this.l = l;
        p2.setBounds(200,0,385,300);
        p2.setBackground(Color.darkGray);
        p2.setBorder(new LineBorder(Color.white, 2));

        generateModes();
        l.loadScores(l.getDifficulty());
        generateScores();

    }

    private ArrayList<JLabel> modes = new ArrayList<>();


    private void generateModes(){
        setBounds(0,0, 200,340);
        setLayout(null);
        setBackground(Color.BLACK);
        modes.add(new JLabel("Difficulty: Easy"));
        modes.add(new JLabel("Difficulty: Medium"));
        modes.add(new JLabel("Difficulty: Hard"));

//        JLabel mode_easy = new JLabel();
//        mode_easy.setText("Difficulty: Easy");
//        mode_easy.setBounds(0,0,200,100);
//        mode_easy.setBackground(Color.DARK_GRAY);
//        mode_easy.setForeground(Color.white);
//        mode_easy.setBorder(new LineBorder(Color.DARK_GRAY, 2));
//        mode_easy.setOpaque(true);
//        add(mode_easy);

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
                    generateScores();
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
    private void generateScores(){

        scores.setText("");
        scores.setBounds(200,0, 496,300);
        scores.setBackground(Color.darkGray);
        scores.setForeground(Color.white);
        scores.setOpaque(true);

        String text ="";

        for (String s : l.getScores()){
            text += s + System.lineSeparator();
        }
        scores.setText(text);
        p2.add(scores);
    }

    public JPanel getP2() {
        return p2;
    }
}
