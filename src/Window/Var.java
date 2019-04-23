package Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Var {

    public static BufferedImage map,panzer,shotIcon,greenBar,greenLock,greenUnlock,shotIconDig,panzerRohr;



    public Var() {
        try {
            map = ImageIO.read(new File("res/map3.png"));
            panzer = ImageIO.read(new File("res/panzer.png"));
            shotIcon = ImageIO.read(new File("res/shotIcon.png"));
            greenBar = ImageIO.read(new File("res/greenBar.png"));
            greenLock = ImageIO.read(new File("res/greenLock.png"));
            greenUnlock = ImageIO.read(new File("res/greenUnlock.png"));
            shotIconDig = ImageIO.read(new File("res/shotIconDig.png"));
            panzerRohr = ImageIO.read(new File("res/rohr.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
