package Model;

import Views.SettingsView;
import Window.MyWindow;

public class SettingsModel {

    public SettingsModel(){
        SettingsView settingsView = new SettingsView(this);
        MyWindow.setContent(settingsView);
    }

    public void backAction(){
        MainMenuModel mainMenuModel = new MainMenuModel();
    }

    public void muteMusikAction(){

    }
}
