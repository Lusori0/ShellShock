package Model;

import Views.SinglePlayerView;
import Window.MyWindow;

import java.util.LinkedList;
import java.util.List;

public class SinglePlayerModel {



    public SinglePlayerModel() {

        MyWindow.setContent(new SinglePlayerView(this));
    }

    public void gegenKiAction(){

    }

    public void sandboxAction(){
        GameModel model = new GameModel(true);
        LinkedList<Player> player = new LinkedList<>();
        player.add(new HumanPlayer(model,1,1,"men"));
        player.add(new KiPlayer(model,2,2));
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.start(player,null,null);
            }
        }).start();


    }

    public void backAction(){
        new MainMenuModel();
    }
}
