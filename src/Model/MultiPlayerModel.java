package Model;

import Views.MainMenuView;
import Views.MultiPlayerView;
import Window.MyWindow;

public class MultiPlayerModel {


    public MultiPlayerModel(){

        MyWindow.setContent(new MultiPlayerView(this));
    }

    public void lokalAction(){
        new PreGameModel();
    }

    public void lanAction(){
        new PreGameModel();
    }

    public void backAction() {new MainMenuModel();}


}
