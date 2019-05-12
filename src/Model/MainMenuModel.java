package Model;

import Views.MainMenuView;
import Window.MyWindow;

public class MainMenuModel {


    public MainMenuModel(){

        MyWindow.setContent(new MainMenuView(this));
    }

    public void singlePlayerAction(){
        new SinglePlayerModel();
    }

    public void multiplayerAction(){
        new MultiPlayerModel();
    }

    public void einstellungenAction(){
        new SettingsModel();
    }

    public void profilAction(){
        new ProfilModel();
    }

    public void exitAction(){
        System.exit(0);
    }
}
