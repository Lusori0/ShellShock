package Window;

import Views.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Var {

    public static BufferedImage map,panzer,shotIcon,greenBar,greenLock,greenUnlock,shotIconDig,panzerRohr;
    public static Sound music;
    public static int soundBarVolume = 0;


    public Var() {
        try {
            background = ImageIO.read(new File("res/background.png"));
            map = ImageIO.read(new File("res/map3.png"));
            panzer = ImageIO.read(new File("res/panzer.png"));
            shotIcon = ImageIO.read(new File("res/shotIcon.png"));
            whiteBar = ImageIO.read(new File("res/whiteBar.png"));
            unselected = ImageIO.read(new File("res/unselected.png"));
            selected = ImageIO.read(new File("res/selected1.png"));
            shotIconDig = ImageIO.read(new File("res/shotIconDig.png"));
            panzerRohr = ImageIO.read(new File("res/rohr.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            music = new Sound();
            music.setVolume(0.5f);
            soundBarVolume = music.getVolume();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

}
