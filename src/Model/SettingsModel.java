package Model;

import Views.SettingsView;
import Views.Sound;
import Window.MyWindow;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class SettingsModel {


    public SettingsModel(){
        MyWindow.setContent(new SettingsView(this));
    }

    public void backAction(){
        new MainMenuModel();
    }

    public void muteMusikAction(){

        Sound.clip.close();
    }

    public void startMusicAction()
    {
        try {
            new Sound();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
}
