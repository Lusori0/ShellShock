package Model;

import Views.SettingsView;

import Window.MyWindow;
import Window.Var;


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

    public void changeMusicVolume(Float f){
        Var.music.setVolume(f);
    }


}
