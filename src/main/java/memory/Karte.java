package memory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Karte {

    private final int ID,
                      POS_X,
                      POS_Y,
                      SIZE_X,
                      SIZE_Y;

    private final Image img;
    private  Image bk_img;
    private Image currentImg;

    private boolean aufgedeckt = false;


    public Karte(int id,  int pos_x,  int pos_y, int size_x, int size_y, Image img, Image bk_img) {
        ID = id;
        POS_Y = pos_y;
        SIZE_X = size_x;
        SIZE_Y = size_y;
        POS_X = pos_x;
        this.img = img;
        this.bk_img = bk_img;
        currentImg = bk_img;
    }

    public void aufdecken(){
        currentImg = img;
    }
    public void zudecken(){
        currentImg = bk_img;
    }


    public int getID() {
        return ID;
    }

    public int getPOS_X() {
        return POS_X;
    }

    public int getPOS_Y() {
        return POS_Y;
    }

    public int getSIZE_X() {
        return SIZE_X;
    }

    public int getSIZE_Y() {
        return SIZE_Y;
    }

    public Image getImg() {
        return img;
    }

    public Image getBk_img() {
        return bk_img;
    }

    public boolean isAufgedeckt() {
        return aufgedeckt;
    }

    public void setAufgedeckt(boolean aufgedeckt) {
        this.aufgedeckt = aufgedeckt;
    }

    public Image getCurrentImg() {
        return currentImg;
    }
}
