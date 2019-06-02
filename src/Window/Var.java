package Window;

import Views.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Var {

    public static BufferedImage map,panzer,shotIcon,whiteBar,unselected,selected,shotIconDig,panzerRohr,background;

    public static BufferedImage shot,bigshot,hughshot,smallbounce,middlebounce,bigbounce,sniper,heavysniper,oneshot,fireball,nuke,meganuke,airstrike,bigairstrike,bombs;



    public static File explosion,engine,fire,shotSound;
    public static AudioInputStream explosionIn,engineIn,fireIn,shotIn;
    public static Clip explosionClip,engineClip,fireClip,shotClip;
    public static float inGameVolume = 0f;
    public static Sound music;
    public static int soundBarVolume = 0;
    public static int[] difficulty_sliderValue_preGameView = new int[8];



    public Var() {

        try {

            explosionClip = AudioSystem.getClip();
            fireClip = AudioSystem.getClip();
            shotClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            shot = ImageIO.read(new File("res/Icons/shot.png"));
            bigshot = ImageIO.read(new File("res/Icons/bigshot.png"));
            hughshot = ImageIO.read(new File("res/Icons/hughshot.png"));

            airstrike = ImageIO.read(new File("res/Icons/airstrike.png"));
            bigairstrike = ImageIO.read(new File("res/Icons/bigairstrike.png"));
            bombs = ImageIO.read(new File("res/Icons/bombs.png"));

            smallbounce = ImageIO.read(new File("res/Icons/smallbounce.png"));
            middlebounce = ImageIO.read(new File("res/Icons/middlebounce.png"));
            bigbounce = ImageIO.read(new File("res/Icons/bigbounce.png"));

            sniper = ImageIO.read(new File("res/Icons/sniper.png"));
            heavysniper = ImageIO.read(new File("res/Icons/heavysniper.png"));
            oneshot = ImageIO.read(new File("res/Icons/oneshot.png"));

            nuke = ImageIO.read(new File("res/Icons/nuke.png"));
            meganuke = ImageIO.read(new File("res/Icons/meganuke.png"));

            fireball = ImageIO.read(new File("res/Icons/fireball.png"));
            map = ImageIO.read(new File("res/map3.png"));
            panzer = ImageIO.read(new File("res/panzer.png"));
            shotIcon = ImageIO.read(new File("res/shotIcon.png"));
            whiteBar = ImageIO.read(new File("res/whiteBar.png"));
            unselected = ImageIO.read(new File("res/unselected.png"));
            selected = ImageIO.read(new File("res/selected1.png"));
            shotIconDig = ImageIO.read(new File("res/shotIconDig.png"));
            panzerRohr = ImageIO.read(new File("res/rohr.png"));

            explosion = new File("res/gameSounds/Explosion.wav");
            engine = new File("res/gameSounds/Motor.wav");
            shotSound = new File("res/gameSounds/Schuss.wav");
            fire = new File("res/gameSounds/knallfeuer.wav");

            explosionIn = AudioSystem.getAudioInputStream(explosion);
            shotIn = AudioSystem.getAudioInputStream(shotSound);

            fireIn = AudioSystem.getAudioInputStream(fire);

            explosionClip.open(explosionIn);
            shotClip.open(shotIn);

            fireClip.open(fireIn);


            inGameVolume = 0f;


            playSound(explosionClip);
            playSound(shotClip);
            playSound(fireClip);


            inGameVolume = 0.5f;



        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
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

    public static void playSound(Clip clip) {
        try{

            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            float gain = (float) (range * Math.log10(inGameVolume * 9 + 1) + volume.getMinimum());
            volume.setValue(gain);




            clip.stop();

            clip.setFramePosition(1);

            clip.start();



        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }

}
