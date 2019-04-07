package Model;

import Views.SettingsView;

import Window.MyWindow;




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
