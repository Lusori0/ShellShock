package Views;

import Window.Var;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class Sound {


    static  File soundFile;

    public static   Clip clip = null;
    int volume;
    boolean muted;
     AudioInputStream in = null;

    public Sound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {


                soundFile = new File("res/sounds/HauptmenüMusic.wav");




            //Clip wird erstellt
            clip = AudioSystem.getClip();
            in = AudioSystem.getAudioInputStream(soundFile);
            clip.open(in);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            muted = false;


    }


    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMuted() {
        return muted;
    }

    //Methode zur Musicmuten und Reaktivierung
    public void muteMusic(){
        if(clip.isRunning()) {
            clip.stop();
            setMuted(true);
        }
        else{
            clip.start();
            setMuted(false);
        }
    }

    public int getVolume()
    {
        return volume;
    }

    // Methode um mit dem Slider das Volumen in gewünschte Lautstätke setzen
    public void setVolume(float volume) {
        this.volume = (int) (volume*100);
        if(volume == 0f) {setMuted(true);}
        if(volume >0f) {setMuted(false);}
        if(!isMuted()){clip.start();}
        if (volume < 0f || volume > 1f)// Abfrage ob gewünschtes Volumen zwischen 0% und 100% ist
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
        Var.soundBarVolume = Var.music.getVolume();
    }



}