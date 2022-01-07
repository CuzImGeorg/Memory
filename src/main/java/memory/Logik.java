package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Logik {
    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;

    private int time;
    private int score;

    private int difficulty;
    private boolean won = false;

    private int counterZuege = 0;


    private int aufgedeckteKartenPaare = 0;

    public Logik(int WINDOW_HEIGHT, int WINDOW_WIGHT, int difficulty) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIGHT = WINDOW_WIGHT;
        this.difficulty = difficulty;
        generateCars();
//        onWin();
    }

    private ArrayList<Karte> karten = new ArrayList<>();

    public void renderCards(Graphics g) {
        for(Karte k : karten){
          g.drawImage(k.getCurrentImg(),  k.getPOS_X(), k.getPOS_Y(), null);
        }
    }

    public void reset(){
        aufgedeckteKartenPaare = 0;
        counterZuege = 0;
        score = 0;
        Start.getMainFrame().getMainPanel().updateScore();
        Start.getMainFrame().getMainPanel().updateCounterCards();
        Start.getMainFrame().getMainPanel().updateCounterZuege();
        try {
            winFrame.dispose();
            winFrame.getWinPanel().getNameSystem().dispose();
        } catch (NullPointerException ignored){}

        karten.clear();
        generateCars();
        ids.clear();
        fillIDS();

    }

    private HashMap<Integer, Integer>  ids = new HashMap<>();

    public void generateCars(){
        fillIDS();
        Image karte_backside;
        ImageIcon temp = new ImageIcon("src/main/java/memory/bk.png");
//        URL resource = this.getClass().getClassLoader().getResource("bk.png");

        Image img = temp.getImage();
        karte_backside = img.getScaledInstance(WINDOW_WIGHT/difficulty-40, 220,  Image.SCALE_SMOOTH);




        Random rdm = new Random();
        for (int i = 0; i < difficulty; i++) {
            for (int j = 0; j < 4; j++) {
               int id = rdm.nextInt(difficulty*4/2);
                while (ids.get(id) == 0) {
                    id = rdm.nextInt(difficulty*4/2);
                }
                ids.replace(id, ids.get(id)-1);
                Image fg_img;
                ImageIcon temp2 = new ImageIcon("src/main/java/memory/"+id+".png");
                Image img2 = temp2.getImage();
                fg_img = img2.getScaledInstance(WINDOW_WIGHT/difficulty-40, 220,  Image.SCALE_SMOOTH);

                karten.add(new Karte(id, i*WINDOW_WIGHT/difficulty +10, j*230+40,  WINDOW_WIGHT/difficulty-40, 210, fg_img, karte_backside));

            }
        }


    }

    private void fillIDS(){
        for (int i = 0; i < difficulty*4/2; i++) {
            ids.put(i,2);
        }
    }

    public void timer(){
        if(time > 0) return;
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(() -> {
            if(won) ses.shutdownNow();
            time++;
        },0,1000, TimeUnit.MILLISECONDS);
    }

    public String getTimeAsString() {
        return time/60 + ":" + time%60;
    }

    public int getTimeAsInt() {
        return time;
    }

    private boolean cd = false;
    public void onMouseClick(MouseEvent e) {
        if(cd) return;
        cd = true;
        toggleCD();

        int x = e.getX();
        int y = e.getY();

        for (Karte k : karten) {
            if(x > k.getPOS_X() && x < k.getPOS_X() + k.getSIZE_X() && y > k.getPOS_Y() && y < k.getPOS_Y() + k.getSIZE_Y()) {

                timer();
                if(!k.isAufgedeckt()){
                    k.aufdecken();
                    k.setAufgedeckt(true);
                    aufgedeckteKarten.add(k);
                }
                checkAufgedeckteKarten();


            }
        }

    }
    private ArrayList<Karte> aufgedeckteKarten = new ArrayList<>();
    private int combo = 1;
    private void checkAufgedeckteKarten(){
        if(aufgedeckteKarten.size() == 2){
            if(aufgedeckteKarten.get(0).getID() == aufgedeckteKarten.get(1).getID()) {
                aufgedeckteKarten.clear();
                aufgedeckteKartenPaare++;
                Start.getMainFrame().getMainPanel().updateCounterCards();
                counterZuege++;
                score+=1000*combo;
                Start.getMainFrame().getMainPanel().updateScore();
                checkIfWon();
                Start.getMainFrame().getMainPanel().updateCounterZuege();
                combo++;
            }else {
                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
                ses.schedule(() -> {
                    aufgedeckteKarten.get(0).zudecken();
                    aufgedeckteKarten.get(0).setAufgedeckt(false);
                    aufgedeckteKarten.get(1).zudecken();
                    aufgedeckteKarten.get(1).setAufgedeckt(false);
                    aufgedeckteKarten.clear();
                    combo = 1;
                    score-=100;
                    Start.getMainFrame().getMainPanel().updateScore();
                    counterZuege++;
                    Start.getMainFrame().getMainPanel().updateCounterZuege();
                },500, TimeUnit.MILLISECONDS);
            }

        }
    }

    private void toggleCD(){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(() -> {
            cd = false;
        }, 550, TimeUnit.MILLISECONDS);
    }
    private WinFrame winFrame;
    public void onWin(){
        won = true;
        winFrame = new WinFrame(this, true);
    }
    public void checkIfnameIsValidAndSafeScore(String playername){
        String name = "";
        if( playername!= null)name = playername;
        if(name.length() == 0) name = "Anonymous";
        String t1 = name.substring(0,1).toUpperCase(Locale.ROOT);
        name = t1 + name.substring(1);
        if(name.length() > 16) name = name.substring(0,16);
        name = name.replace(':', '?');

        safeScore(difficulty,  getTimeAsString() + ":" + name + ":" + score );
        loadScores(difficulty);
        winFrame.getWinPanel().getP2().removeAll();
        winFrame.getWinPanel().generateScores();
        winFrame.getWinPanel().loadScores();
    }

    public void checkIfWon(){
        for(Karte k : karten)  {
            if(!k.isAufgedeckt()){
                return;
            }
        }
        onWin();

    }

    private ArrayList<String> scores = new ArrayList<>();
    public void loadScores(int mode) {

        scores.clear();
        String diff;

        switch (mode){
            case 4 ->  diff = "easy";
            case 5 ->  diff = "medium";
            case 6 -> diff = "hard";
            default -> diff = "";
        }


        Scanner s = null;

        try {
            File file = new File("src/main/java/memory/scores_" + diff + ".txt");

            s  = new Scanner(file);
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while(s.hasNext()) {
            String str = s.next();
            scores.add(str);
        }

    }
    public void safeScore(int mode, String s){
        Collections.sort(scores);
        String diff;
        switch (mode){
            case 4 ->  diff = "easy";
            case 5 ->  diff = "medium";
            case 6 -> diff = "hard";
            default -> diff = "";
        }
        FileWriter fw;
        try {
            fw = new FileWriter("src/main/java/memory/scores_" + diff + ".txt");
            String w = "";
            for(String temp: scores){
                w += temp+ System.lineSeparator();
            }
            w += s;
            fw.write(w);
            fw.close();

        } catch (IOException ignored) {}
    }
    private ArrayList<String> toSort = new ArrayList<>();
    public ArrayList<String> getScores() {
        return scores;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getDifficultyAsString() {
        String diff;
        switch (difficulty){
            case 4 ->  diff = "easy";
            case 5 ->  diff = "medium";
            case 6 -> diff = "hard";
            default -> diff = "";
        }
        return diff;
    }

    public int getCounterZuege() {
        return counterZuege;
    }

    public int getScore() {
        return score;
    }

    public int getAufgedeckteKartenPaare() {
        return aufgedeckteKartenPaare;
    }
}
