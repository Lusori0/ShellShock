package Model;

import Views.MainMenuView;
import Views.MultiPlayerView;
import Window.MyWindow;

public class MultiPlayerModel {


    public MultiPlayerModel(){

        MyWindow.setContent(new MultiPlayerView(this));
    }

    public void lokalAction(){
        new PreGameModel(GameType.MULTI_LOCAL,false);
    }

    public void lanAction(){
        new PreGameModel(GameType.MULTI_LAN,false);
    }

    public void backAction() {new MainMenuModel();}


}
