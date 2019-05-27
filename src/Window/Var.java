package Window;

import Views.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Var {

    public static BufferedImage map,panzer,shotIcon,greenBar,greenLock,greenUnlock,shotIconDig,panzerRohr,background,whiteBar,unselected,selected;
    public static Sound music;
    public static int soundBarVolume = 0;



    public static File test = new File("res/test.wav");
    private static float inGameVolume = 0.4f;


    public Var() {
        try {
            //background = ImageIO.read(new File("res/background.png")); Nils dem sein background
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

    public static void playSound(File soundFile) {
        try{

            //Clip wird erstellt
            Clip clip = AudioSystem.getClip();
            AudioInputStream in = AudioSystem.getAudioInputStream(soundFile);
            clip.open(in);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (float) (range * Math.log10(inGameVolume * 9 + 1) + volume.getMinimum());
            System.out.println(gain);
            volume.setValue(gain);


            clip.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }

}
