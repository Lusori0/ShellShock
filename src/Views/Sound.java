package Views;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Sound {


    static  File soundFile;

    public static   Clip clip = null;
     AudioInputStream in = null;

    public Sound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        //Zugriff auf die Sound-Datei für das Game(If-Abfrage für verschiedeme Betriebssysteme)
        if(System.getProperty("os.name").contains("Win")) {
                //Windows
                soundFile = new File("res\\sounds\\HauptmenüMusic.wav");
            }
            else{
                // Linux
                soundFile = new File("res/sounds/HauptmenüMusic.wav");
            }



            //Clip wird erstellt
            clip = AudioSystem.getClip();
            in = AudioSystem.getAudioInputStream(soundFile);
            clip.open(in);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            clip.start();


    }
    //Methode zur Musicmuten und Reaktivierung
    public void muteMusic(){
        if(clip.isRunning()) {
            clip.stop();
        }
        else{
            clip.start();
        }
    }
    // Volume Slider test
    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void setVolume(float volume) {
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }



}