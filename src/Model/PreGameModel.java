package Model;

import Views.PreGameView;
import Window.*;


import java.io.PrintStream;
import java.util.LinkedList;
import java.awt.*;

public class PreGameModel {

    private GameType gametype;
    private final boolean sandbox;

    public PreGameModel(GameType gametype, boolean sandbox) {
        this.gametype = gametype;
        this.sandbox = sandbox;
        MyWindow.setContent(new PreGameView(this));
    }

    public void gameSettingsAction(){

    }

    public void startAction(int amount,int[] difficutly){
        GameModel model = new GameModel();
        LinkedList<Player> players = new LinkedList<>();

        LinkedList<Integer> weaponsTest = new LinkedList<>();

        weaponsTest.add(1);
        weaponsTest.add(4);
        weaponsTest.add(5);

        Profil profil = new Profil("test",1,"test",1,weaponsTest);

        players.add(new HumanPlayer(model,1,model.getNextId(), Var.activeUser));
        players.add(new HumanPlayer(model,2,model.getNextId(), profil));
        players.add(new HumanPlayer(model,2,model.getNextId(), profil));
        players.add(new HumanPlayer(model,2,model.getNextId(), profil));
        if(amount >0)
        {
            for(int i = 0;i<amount;i++)
            {
                //players.add(new KiPlayer(model,2,model.getNextId(), difficutly[i],Var.activeUser));
            }
        } else{}

        new Thread(() -> model.start(players, false,new Color(0,200,255),Color.GREEN,6,1)).start();


    }

    public void backAction(){
        new MainMenuModel();
    }

    public void startAction(LinkedList<Player> players, Color background, Color foreground){
        GameModel model = new GameModel();
        new Thread(() -> model.start(players,sandbox, background, foreground,6,1)).start();

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