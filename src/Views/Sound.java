package Views;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Sound {


    static  File soundFile = new File("res\\sounds\\HauptmenüMusic.wav");

    public static   Clip clip = null;
     AudioInputStream in = null;

    public Sound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {


            clip = AudioSystem.getClip();
            in = AudioSystem.getAudioInputStream(soundFile);
            clip.open(in);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();


    }



}