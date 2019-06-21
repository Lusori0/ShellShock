package Model;

import Views.GameView;
import Views.SettingsView;

import Window.MyWindow;
import Window.Var;


public class SettingsModel {

    private boolean fromGame;
    private GameView gameView;


    public SettingsModel(){
        fromGame = false;
        MyWindow.setContent(new SettingsView(this));
    }

    public SettingsModel(GameView gameView) {
        fromGame = true;
        this.gameView = gameView;
        MyWindow.setContent(new SettingsView(this));
    }

    public void backAction(){
        if(!fromGame) {
            new MainMenuModel();
        }else{
            MyWindow.setContent(gameView);
        }
    }

    public void muteMusikAction(){

        MyWindow.muteMusic();
    }

    public void changeMusicVolume(Float f){
        Var.music.setVolume(f);
        Var.activeUser.setMusicVolume(f);
    }
}
