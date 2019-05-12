package Model;

import Views.PreGameView;
import Window.MyWindow;

public class PreGameModel {

    public PreGameModel() {
        MyWindow.setContent(new PreGameView(this));
    }

    public void gameSettingsAction(){

    }

    public void startAction(){
            new GameModel();
    }

    public void backAction(){
        new MainMenuModel();
    }
}
