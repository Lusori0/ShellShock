package Model;

import Views.GameLoop;
import Views.GameView;


import Weapons.Weapon;
import Window.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class GameModel {

    private GameView gameView;
    private GameLoop gameLoop;
    private LinkedList<Player>  players;
    private LinkedList<Weapon> currentWeapons;
    private GameMap map;
    private int height;
    private Player currentPlayer,lastLocalHuman;
    private boolean shot;
    private int weaponsShowedTime = 0;

    public GameModel(){
        //map = Var.map;

        //collisionMap = new CollisionMap(map,this);

        height = MyWindow.HEIGHT-MyWindow.HEIGHT/4;
        gameView = new GameView();


        map = new GameMap(this);



        players = new LinkedList<>();

        //Erster spieler ist ein lokaler mensch

        players.add(new HumanPlayer(this,1));
        players.add(new KiPlayer(this,2));

        currentWeapons = new LinkedList<>();

        currentPlayer = players.getFirst();

        lastLocalHuman = currentPlayer;



        gameLoop = new GameLoop(this);
        gameView.add(gameLoop, BorderLayout.CENTER);

        MyWindow.setContent(gameView);


        for(Player player : players){
            player.getPanzer().moveNotTurn(map);
        }




    }



    public GameMap getMap(){
        return map;
    }

    public void test(Graphics2D g2d){

    }

    public void setMap(BufferedImage map) {
        //this.map = map;
    }

    public void drawPanzer(Graphics2D g2d){
        for(Player player : players){
            if(player == lastLocalHuman) {
                player.getPanzer().draw(g2d,0);
            }else if(player.getTeam() == lastLocalHuman.getTeam()){
                player.getPanzer().draw(g2d,1);
            }else{
                player.getPanzer().draw(g2d,2);
            }
        }
    }

    public void movePanzer(){
        for(Player player : players){
            if(player == currentPlayer) {
                if(!shot) {
                    if(player.isKi()) {
                        player.move(this);
                    }else if(player.isLocalHuman()) {

                        player.getPanzer().setMove();
                    }
                    player.getPanzer().move(map);
                }
            }else{
                player.getPanzer().moveNotTurn(map);
            }
        }
    }



    public int getHeight() {
        return height;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public Player nextPlayer(){
        int index = players.indexOf(currentPlayer);

        currentPlayer.getPanzer().resetSprit();

        if(index < players.indexOf(players.getLast())) {
            shot = false;
            return players.get(index + 1);

        }else{

            shot = false;
            return players.getFirst();
        }


    }

    public void removeWeapon(Weapon weapon){
        currentWeapons.remove(weapon);
    }

    public void changeGui(){

    }

    public void nextTurn(){
        for(Player player : players){
            if(player.getPanzer().getLeben() <=0){
                players.remove(player);
            }
        }
        if(players.size()>1) {
            currentPlayer = nextPlayer();
            changeGui();
            if(currentPlayer.isLocalHuman()){
                lastLocalHuman = currentPlayer;
            }else if(currentPlayer.isKi()){
                currentPlayer.prepare(this);
            }
        }else{
            endGame();
        }
    }

    public void endGame(){

    }

    public void handleMousePressed(int x,int y){
        if(!shot) {
            if (currentPlayer.isLocalHuman()) {
                if (currentPlayer.getPanzer().isSelected()) {


                    currentPlayer.getPanzer().setTemps();

                    currentPlayer.getPanzer().changeRohr(x,y,this);

                    currentPlayer.getPanzer().setShootready(true);


                }
            }
        }
    }

    public void handleMouseClicked(int x,int y){
        if(!shot) {
            if (currentPlayer.isLocalHuman()) {
                if (currentPlayer.getPanzer().isSelected()) {

                    currentPlayer.getPanzer().setTemps();

                    currentPlayer.getPanzer().changeRohr(x,y,this);

                    currentPlayer.getPanzer().setShootready(true);




                }
                if (currentPlayer.getPanzer().isHit(x, y)) {
                    currentPlayer.getPanzer().setSelected(true);
                }

            }
        }
    }

    public void drawUi(Graphics2D g2d,int x,int y) {
        if (currentPlayer != null) {
            if (currentPlayer.getPanzer().isSelected()) {
                if(!shot) {
                    currentPlayer.getPanzer().drawUi(g2d,x,y,this);
                }
            }
        }
    }

    public void setWeaponsShowTime(int t){
        weaponsShowedTime  = t;
    }

    public int getWeaponsShowedTime() {
        return weaponsShowedTime;
    }

    public void showWeapons(Graphics2D g2d, int mousex, int mousey, boolean clicked,int art){

        if(weaponsShowedTime <= getHeight()/20) {
            g2d.fillRect(0, getHeight() - 20 * weaponsShowedTime, MyWindow.WIDTH, (int) 20 * weaponsShowedTime);
        }else{
            g2d.fillRect(0, 0, MyWindow.WIDTH, getHeight());

        }





        int xtemp = MyWindow.WIDTH/5;
        int ytemp = height/8;

        int size = lastLocalHuman.getWeapons().size();



        int h= size/5;

        if(size%5 != 0){
            h++;
        }

        int t = 0;

        boolean temp = false,handeld = false;

        for(Weapon weapon : lastLocalHuman.getWeapons()){
            if(weaponsShowedTime/5 > t) {

                if (new Rectangle2D.Double(t % 5 * xtemp, (t / 5) * ytemp + height - h * ytemp, xtemp, ytemp).contains(mousex, mousey)) {
                    if (clicked) {
                        MyKeys.weapon = false;
                        lastLocalHuman.setSelectedWeapon(weapon);
                        handeld = true;
                    } else {
                        weapon.drawImage((int) (t % 5 * xtemp - xtemp * 0.1), (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1), (int) (xtemp * 1.2), (int) (ytemp * 1.2), g2d);
                    }
                } else {
                    weapon.drawImage(t % 5 * xtemp, (t / 5) * ytemp + height - h * ytemp, xtemp, ytemp, g2d);
                }

                t++;
            }else{
                temp = true;
            }
        }

        if(temp || art < 0){
            weaponsShowedTime += art;
        }

        if(clicked && !handeld){
            MyKeys.weapon = false;
        }


    }

    public void drawCurrentWeapons(Graphics2D g2d){
        for(Weapon weapon : currentWeapons){
            weapon.draw(g2d);
        }
    }

    public boolean isCollisionPanzer(int x,int y){
        for(Player player : players){
            if(player != currentPlayer) {
                if (player.getPanzer().isHit(x, y)) {
                    return true;
                }
            }
        }
        return map.isCollision(x,y);
    }

    public void feuerButtonAction(){

        if(!shot) {

            currentPlayer.getSelectedWeapon().create((int)(currentPlayer.getPanzer().getBulletspawn().getX()), (int) currentPlayer.getPanzer().getBulletspawn().getY(),
                    currentPlayer.getPanzer().getRohrWinkel(), currentPlayer.getPanzer().getShotstrength(), currentPlayer.getPanzer().isOrientationRight());


            currentWeapons.add(currentPlayer.getSelectedWeapon());
            shot = true;

            currentPlayer.getWeapons().remove(currentPlayer.getSelectedWeapon());

            currentPlayer.setSelectedWeapon(currentPlayer.getWeapons().getFirst());


        }
    }

    public void addWeapon(Weapon weapon){
        currentWeapons.add(weapon);
    }


    public void explosion(int x,int y,int size,int damage){
        map.explosion(x,y,size,damage);
    }



    public void movePanzerDown(int x,int y,int size,int damage) {



        for(Player player : players){

            player.getPanzer().moveNotTurn(map);

            if(new Ellipse2D.Double(x - size/(double)2,y -size/(double)2,size,size).intersects(player.getPanzer().getHitbox().getBounds2D())){



                if(player != currentPlayer) {

                    player.getPanzer().schaden(damage, 0);
                }else{

                    player.getPanzer().schaden(damage, 1);
                }
            }

        }
    }

    public Player getGegener(){
        for(Player player : players){
            if(player.getTeam() != currentPlayer.getTeam()){
                return player;
            }
        }
        return null;
    }
}
