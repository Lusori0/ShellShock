package Model;

import Views.PreGameView;
import Window.MyWindow;

import java.util.LinkedList;
import java.awt.*;

public class PreGameModel {

    private GameType gametype;

    public PreGameModel(GameType gametype) {
        this.gametype = gametype;
        MyWindow.setContent(new PreGameView(this));
    }

    public void gameSettingsAction(){

    }

    public void startAction(){
        GameModel model = new GameModel();
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(model,1,1,"name"));
        players.add(new KiPlayer(model,2,2, 10));
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.start(players,null,null, true,Color.WHITE,Color.BLACK);
            }
        }).start();

    }

    public void backAction(){
        new MainMenuModel();
    }
}

/*
////////////////Reminder/////////////////////////////
GameModel model = new GameModel();
        LinkedList<Player> players = new LinkedList<>();
        players.add(new HumanPlayer(model,1,1,"name"));
        players.add(new KiPlayer(model,2,2, 10));
        new Thread(new Runnable() {
            @Override
            public void run() {
                model.start(players,null,null, true,Color.WHITE,Color.BLACK);
            }
        }).start();
////////////////////////////////////////////////////
 */