package Model;



import Window.*;

public class HumanPlayer extends Player {



    public HumanPlayer(GameModel model, int team,int id,Profil profil) {
        super(model, team,id,profil);

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

    @Override
    public void prepare(GameModel model) {

    }

    @Override
    public void shoot(GameModel model) {

        getSelectedWeapon().create((int)(getPanzer().getBulletspawn().getX()), (int) getPanzer().getBulletspawn().getY(),
                getPanzer().getRohrWinkel(), getPanzer().getShotstrength(), getPanzer().isOrientationRight(),getPanzer());

    }

    @Override
    public void setValues(int x, int y, double drawWinkel, double winkel,double rohrwinkel,boolean right) {}
}
