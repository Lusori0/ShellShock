package Model;

import Views.MainMenuView;
import Window.MyWindow;

public class MainMenuModel {


    public MainMenuModel(){
        MainMenuView mainMenuView = new MainMenuView(this);
        MyWindow.setContent(mainMenuView);
    }

    public void singlePlayerAction(){
        SinglePlayerModel singlePlayerModel= new SinglePlayerModel();
    }

    public void multiplayerAction(){

    }

    public void einstellungenAction(){
        SettingsModel settingsModel = new SettingsModel();
    }

    public void profilAction(){
        ProfilModel profilModel = new ProfilModel();
    }

    public void exitAction(){
        System.exit(0);
    }
}
