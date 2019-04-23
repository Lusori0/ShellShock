package Model;

import Views.SinglePlayerView;
import Window.MyWindow;

public class SinglePlayerModel {



    public SinglePlayerModel() {

        MyWindow.setContent(new SinglePlayerView(this));
    }

    public void gegenKiAction(){

    }

    public void sandboxAction(){
        new GameModel();
    }

    public void backAction(){
        new MainMenuModel();
    }
}
