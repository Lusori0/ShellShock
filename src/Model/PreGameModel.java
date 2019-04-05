package Model;

import Views.PreGameView;
import Window.MyWindow;

public class PreGameModel {
    PreGameView preGameView;

    public PreGameModel() {
        preGameView = new PreGameView(this);

        MyWindow.setContent(preGameView);
    }

    public void gameSettingsAction(){

    }

    public void startAction(){

    }

    public void backAction(){
        MainMenuModel mainMenuModel = new MainMenuModel();
    }
}
