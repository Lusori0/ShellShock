package Model;


import Views.MultiPlayerView;
import Window.MyWindow;

public class MultiPlayerModel {


    public MultiPlayerModel(){

        MyWindow.setContent(new MultiPlayerView(this));
    }
    // button actions

    public void lokalAction(){
        new PreGameModel(GameType.MULTI_LOCAL,false,true,null);
    }

    public void backAction() {new MainMenuModel();}


}
