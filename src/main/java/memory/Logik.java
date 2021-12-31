package memory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Logik {
    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;

    private int time;

    private int difficulty;
    private boolean won = false;

    private  Image karte_backside;


    public Logik(int WINDOW_HEIGHT, int WINDOW_WIGHT, int difficulty) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIGHT = WINDOW_WIGHT;
        this.difficulty = difficulty;
        generateCars();
        onWin();
    }

    private ArrayList<Karte> karten = new ArrayList<>();

    public void renderCards(Graphics g) {
        for(Karte k : karten){
            g.drawImage(k.getCurrentImg(),  k.getPOS_X(), k.getPOS_Y(), null);
        }
    }

    public void reset(){
        karten.clear();
        generateCars();
        ids.clear();
        fillIDS();

    }

    private HashMap<Integer, Integer> ids = new HashMap<>();

    public void generateCars(){
        fillIDS();
        Image karte_backside = null;
        ImageIcon temp = new ImageIcon("src/main/java/memory/bk.png");
//        URL resource = this.getClass().getClassLoader().getResource("bk.png");

        Image img = temp.getImage();
        karte_backside = img.getScaledInstance((int) WINDOW_WIGHT/difficulty-40, 220,  Image.SCALE_SMOOTH);




        Random rdm = new Random();
        for (int i = 0; i < difficulty; i++) {
            for (int j = 0; j < 4; j++) {
               int id = rdm.nextInt(difficulty*difficulty/2);
                while (ids.get(id) == 0) {
                    id = rdm.nextInt(difficulty*difficulty/2);
                }
                ids.replace(id, ids.get(id)-1);

                Image fg_img = null;
//                URL resource2 = this.getClass().getClassLoader().getResource(id +".png");
                ImageIcon temp2 = new ImageIcon("src/main/java/memory/"+id+".png");
//                ImageIcon temp2 = new ImageIcon(resource2.getFile());
                Image img2 = temp2.getImage();
                fg_img = img2.getScaledInstance((int) WINDOW_WIGHT/difficulty-40, 220,  Image.SCALE_SMOOTH);

                karten.add(new Karte(id, i*WINDOW_WIGHT/difficulty +10, j*230+40,  (int )WINDOW_WIGHT/difficulty-40, 210, fg_img, karte_backside));

            }
        }


    }

    private void fillIDS(){
        for (int i = 0; i < difficulty*difficulty/2; i++) {
            ids.put(i,2);
        }
    }

    public void timer(){
        // start Timer noch 1 Karte kick
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(won) ses.shutdownNow();
                time++;
            }
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
    private void checkAufgedeckteKarten(){
        if(aufgedeckteKarten.size() == 2){
            if(aufgedeckteKarten.get(0).getID() == aufgedeckteKarten.get(1).getID()) {
                aufgedeckteKarten.clear();
                checkIfWon();
            }else {
                ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
                ses.schedule(new Runnable() {
                    @Override
                    public void run() {
                        aufgedeckteKarten.get(0).zudecken();
                        aufgedeckteKarten.get(0).setAufgedeckt(false);
                        aufgedeckteKarten.get(1).zudecken();
                        aufgedeckteKarten.get(1).setAufgedeckt(false);
                        aufgedeckteKarten.clear();
                    }
                },500, TimeUnit.MILLISECONDS);
            }

        }
    }

    private void toggleCD(){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ses.schedule(new Runnable() {
            @Override
            public void run() {
                cd = false;
            }
        }, 550, TimeUnit.MILLISECONDS);
    }
    public void onWin(){
        System.out.println("you won");
        won = true;
        WinFrame f = new WinFrame(this);
        //TODO better name input
        String name = JOptionPane.showInputDialog("name");
        if(name.length() > 16) name = name.substring(0,16);
           name = name.replace(':', '?');

        safeScore(difficulty,  name + ": " + getTimeAsString());
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
        System.out.println(mode);
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
        String diff;
        System.out.println(mode);
        switch (mode){
            case 4 ->  diff = "easy";
            case 5 ->  diff = "medium";
            case 6 -> diff = "hard";
            default -> diff = "";
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter("src/main/java/memory/scores_" + diff + ".txt");
            String w = "";
            for(String temp: scores){
                w += temp+ System.lineSeparator();

            }
            w += s;
            fw.write(w);
            fw.close();
        } catch (IOException e) {}
    }

    public ArrayList<String> getScores() {
        return scores;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
