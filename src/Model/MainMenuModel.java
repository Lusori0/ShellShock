package Model;

import Views.MainMenuView;
import Window.MyWindow;

public class MainMenuModel {

    private MainMenuView mainMenuView;

    public MainMenuModel(){
        mainMenuView = new MainMenuView(this);
        MyWindow.setContent(mainMenuView);
    }

}
