package Views;

import Model.MainMenuModel;
import Model.SinglePlayerModel;
import Window.ButtonAction;
import Window.MyButton;

import javax.swing.*;

public class MainMenuView extends JPanel {

    MainMenuModel mainMenuModel;

    MyButton singelplayer,multiplayer;

    public MainMenuView(MainMenuModel mainMenuModel) {
        this.mainMenuModel = mainMenuModel;

        singelplayer = new MyButton(singelPlayerAction(),"Einzelspieler");

        multiplayer = new MyButton(multiplayerAction(),"Mehrspieler");

        this.add(singelplayer);
        this.add(multiplayer);
    }

    public ButtonAction multiplayerAction(){
        return () -> singelplayer.doClick();
    }

    public ButtonAction singelPlayerAction(){
        return SinglePlayerModel::new;
    }


}
