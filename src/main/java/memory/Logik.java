package memory;

import com.sun.tools.javac.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Logik {
    private final int WINDOW_HEIGHT,
                      WINDOW_WIGHT;

    private int difficulty;

    private  Image karte_backside;


    public Logik(int WINDOW_HEIGHT, int WINDOW_WIGHT, int difficulty) {
        this.WINDOW_HEIGHT = WINDOW_HEIGHT;
        this.WINDOW_WIGHT = WINDOW_WIGHT;
        this.difficulty = difficulty;
        generateCars();
    }

    private ArrayList<Karte> karten = new ArrayList<>();

    public void renderCards(Graphics g) {
        for(Karte k : karten){
            g.drawImage(k.getCurrentImg(),  k.getPOS_X(), k.getPOS_Y(), null);
        }
    }

    private HashMap<Integer, Integer> ids = new HashMap<>();

    private void generateCars(){
        fillIDS();
        Image karte_backside = null;
        ImageIcon temp = new ImageIcon("src/main/java/memory/bk.png");
//        URL resource = this.getClass().getClassLoader().getResource("bk.png");

        Image img = temp.getImage();
        karte_backside = img.getScaledInstance((int) WINDOW_WIGHT/difficulty-40, 220,  Image.SCALE_SMOOTH);



        System.out.println(difficulty);
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




    private boolean cd = false;
    public void onMouseClick(MouseEvent e) {
        if(cd) return;
        cd = true;
        toggleCD();

        int x = e.getX();
        int y = e.getY();

        for (Karte k : karten) {
            if(x > k.getPOS_X() && x < k.getPOS_X() + k.getSIZE_X() && y > k.getPOS_Y() && y < k.getPOS_Y() + k.getSIZE_Y()) {
                System.out.println(k.getID());
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
            }else {
                System.out.println("ko");

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
        }, 400, TimeUnit.MILLISECONDS);
    }


    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
