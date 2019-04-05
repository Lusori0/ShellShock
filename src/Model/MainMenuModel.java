package Model;

import Views.MainMenuView;
import Window.MyWindow;

public class MainMenuModel {

    private MainMenuView mainMenuView;

    public MainMenuModel(){
        mainMenuView = new MainMenuView(this);
        MyWindow.setContent(mainMenuView);
    }

    public void singlePlayerAction(){
        SinglePlayerModel singlePlayerModel= new SinglePlayerModel();
    }

    public void multiplayerAction(){

    }

}
