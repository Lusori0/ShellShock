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

        MyWindow.muteMusic();
    }


}
