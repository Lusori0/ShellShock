package Views;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Sound {


    static  File soundFile;

    public static   Clip clip = null;
     AudioInputStream in = null;

    public Sound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {


            if(System.getProperty("os.name").contains("Win")) {
                soundFile = new File("res\\sounds\\HauptmenüMusic.wav");
            }
            else{
                soundFile = new File("res/sounds/HauptmenüMusic.wav");
            }




            clip = AudioSystem.getClip();
            in = AudioSystem.getAudioInputStream(soundFile);
            clip.open(in);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();


    }

    public void muteMusic(){
        if(clip.isRunning()) {
            clip.stop();
        }
        else{
            clip.start();
        }
    }



}