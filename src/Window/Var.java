package Window;

import Model.Profil;
import Views.Sound;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Var {

    public static BufferedImage map,panzer,shotIcon,whiteBar,unselected,selected,shotIconDig,panzerRohr,drop,panzerNormalGanz,lightPanzer,lightPanzerGanz,heavyPanzer,heavyPanzerGanz;

    public static BufferedImage shot,bigshot,hughshot,smallbounce,middlebounce,bigbounce,sniper,heavysniper,oneshot,fireball,nuke,meganuke,airstrike,bigairstrike,bombs,laser ,biglaser,granade,trinade,trishot,quadshot,multishot,shotgun,mg,ghostbomb;





    public static File explosion,engine,fire,shotSound;
    public static AudioInputStream explosionIn,engineIn,fireIn,shotIn;
    public static Clip explosionClip,engineClip,fireClip,shotClip;
    public static float inGameVolume = 0f;
    public static Sound music;
    public static int soundBarVolume = 0;
    public static float ingameSoundbar = 0f;
    public static int[] difficulty_sliderValue_preGameView = new int[8];
    public static Profil activeUser;
    public static Profil[] login_profils = new Profil[5];
    public static  boolean [] selected_humanCheckbox_speicher = new boolean[5];
    public static  boolean [] selected_aiCheckbox_speicher = new boolean[7];
    public static int redtemp = 120,bluetemp = 80,greentemp = 100,red_btemp = 100,green_btemp  = 100,blue_btemp = 100;//Speichern der RGB Slider Werte

    public Var() {

        try {

            explosionClip = AudioSystem.getClip();
            fireClip = AudioSystem.getClip();
            shotClip = AudioSystem.getClip();
        } catch (LineUnavailableException | IllegalArgumentException e ) {
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

            laser = ImageIO.read(new File("res/Icons/laser.png"));
            biglaser = ImageIO.read(new File("res/Icons/biglaser.png"));

            granade = ImageIO.read(new File("res/Icons/grenade.png"));
            trinade = ImageIO.read(new File("res/Icons/trinade.png"));

            shotgun = ImageIO.read(new File("res/Icons/shotgun.png"));
            mg = ImageIO.read(new File("res/Icons/mg.png"));

            trishot = ImageIO.read(new File("res/Icons/tripleshot.png"));
            quadshot = ImageIO.read(new File("res/Icons/quadshot.png"));
            multishot = ImageIO.read(new File("res/Icons/multishot.png"));

            ghostbomb = ImageIO.read(new File("res/Icons/ghostbomb.png"));

            drop = ImageIO.read(new File("res/Icons/supply.png"));

            map = ImageIO.read(new File("res/map3.png"));

            panzer = ImageIO.read(new File("res/panzer.png"));
            lightPanzer = ImageIO.read(new File("res/lightPanzer.png"));
            heavyPanzer = ImageIO.read(new File("res/heavyPanzer.png"));

            panzerNormalGanz = ImageIO.read(new File("res/buttons/PanzerDefaultMetallic1.png"));
            lightPanzerGanz = ImageIO.read(new File("res/buttons/PanzerLightMetallic.png"));
            heavyPanzerGanz = ImageIO.read(new File("res/buttons/PanzerHeavyMetallic.png"));

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


            inGameVolume = 0;


            playSound(explosionClip);
            playSound(shotClip);
            playSound(fireClip);


            inGameVolume = 0.5f;



        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            music = new Sound();
            music.setVolume(0.1f);
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

    public static void setActiveUser(Profil profilRead) {
        System.out.println("active user set");
        activeUser = profilRead;
        inGameVolume = activeUser.getInGameVolume();
        ingameSoundbar = activeUser.getInGameVolume();
        music.setVolume(activeUser.getMusicVolume());
        soundBarVolume = (int)(100*activeUser.getMusicVolume());
    }
}
