package Model;

import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

import Network.Client;
import Window.*;

public class HumanPlayer extends Player {

    private Client client;

    public HumanPlayer(GameModel model, int team,int id,String name) {
        super(model, team,id,name);
    }

    public HumanPlayer(GameModel model, int team,int id,String name, String ipAdress){
        super(model,team,id,name);

    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean isLocalHuman() {
        return true;
    }

    @Override
    public int action(GameModel model) {

        return 1;

    }

    @Override
    public void move(GameModel model,GameMap map) {
        if(isOnTurn()) {

            if (MyKeys.right) {

                getPanzer().setMoveRight(true);
            } else if (MyKeys.left) {
                getPanzer().setMoveLeft(true);
            }

            getPanzer().move(map);
        }else{
            getPanzer().moveNotTurn(map);
        }
    }

    public void sendTCP(String message){
        client.sendTCP(message);
    }

    @Override
    public void prepare(GameModel model) {

    }

    @Override
    public void shoot(GameModel model) {

        getSelectedWeapon().create((int)(getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                getPanzer().getRohrWinkel(), getPanzer().getShotstrength(), getPanzer().isOrientationRight(),getPanzer());

    }

    public void sendToServer(){
        String xPosition = String.valueOf((int)getPanzer().getxPosition());
        String yPosition = String.valueOf((int)getPanzer().getyPosition());
        String drawWinkel = String.valueOf(getPanzer().getDrawWinkel());
        String winkel = String.valueOf(getPanzer().getWinkel());
        String rohrwinkel = String.valueOf(getPanzer().getRohrWinkel());
        String right = String.valueOf(getPanzer().isOrientationRight());

        client.sendData(("move:" + getId() + ":"+xPosition + ":" + yPosition + ":" + drawWinkel + ":" + winkel + ":" + rohrwinkel + ":" + right).getBytes());
    }

    @Override
    public void setValues(int x, int y, double drawWinkel, double winkel,double rohrwinkel,boolean right) {}
}
