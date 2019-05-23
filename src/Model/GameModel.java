package Model;

import Network.Client;
import Network.Server;
import Panzer.Panzer;
import Views.GameLoop;
import Views.GameUiView;
import Views.GameView;


import Weapons.Weapon;
import Window.*;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameModel {

    private GameView gameView;
    private GameLoop gameLoop;
    private LinkedList<Player>  players;
    private CopyOnWriteArrayList<Weapon> currentWeapons;
    private GameMap map;
    private int height;
    private Player lastLocalHuman;
    private LinkedList<Player> currentPlayer;
    private boolean shot;
    private int weaponsShowedTime = 0;
    private int spielmode = 0,teamanzahl = 0;

    private boolean isServer,network;
    private Server server;

    private int highId = 0;

    private double redTriTimer = 0;
    private boolean sandbox;
    private GameUiView gameUiView;


    public GameModel(){
        //map = Var.map;

        //collisionMap = new CollisionMap(map,this);

        height = (int) (GameLoop.imgH);
        gameView = new GameView();

        gameView.addKeyListener(new MyKeys());

        map = new GameMap(this,5);

        players = new LinkedList<>();
        currentPlayer = new LinkedList<>();

        //Erster spieler ist ein lokaler mensch

        teamanzahl = 2;

        spielmode = 3;

        currentWeapons = new CopyOnWriteArrayList<>();
    }

    public void start(LinkedList<Player> players,Client client,Server server,boolean sandbox,Color background,Color forground){

        this.sandbox = sandbox;


        if(server != null || client != null){
            network = true;
        }

        if(server != null){
            this.server = server;
            isServer = true;
        }

        this.players = players;

        if(spielmode == 1){
            currentPlayer.add(players.getFirst());
        }else if(spielmode == 2){
            int team = players.getFirst().getTeam();

            for(Player player : players){
                if(player.getTeam() == team){
                    currentPlayer.add(player);
                }
            }
        }else{
            currentPlayer.addAll(players);
        }

        for(Player player : currentPlayer) {
            player.setOnTurn(true);
            if(player.isLocalHuman()){
                lastLocalHuman = player;

                if(network && !isServer) {
                    ((HumanPlayer) player).setClient(client);
                }
            }
        }

        gameLoop = new GameLoop(this,background);
        gameView.add(gameLoop, BorderLayout.CENTER);
        gameUiView = new GameUiView(this);
        gameView.add(gameUiView,BorderLayout.PAGE_END);
        MyWindow.setContent(gameView);
        gameLoop.createStrategy();
        gameLoop.mainGameLoop();





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

            if(currentPlayer.contains(player)){
                if(!shot) {
                    drawRedRect(player, g2d);
                }
            }
        }
    }

    private void drawRedRect(Player player,Graphics2D g2d){

        int timerAdd = (int) Math.abs(redTriTimer);

        int triSize = 10;
        int[] xPos = new int[]{(int) (player.getPanzer().getBulletspawn().getX() - triSize), (int) player.getPanzer().getBulletspawn().getX(), (int) (player.getPanzer().getBulletspawn().getX() + triSize)};
        int ytemp = (int) (player.getPanzer().getCenter().getY() - 50 - timerAdd);
        int[] yPos = new int[]{ytemp - triSize,ytemp,ytemp - triSize};

        g2d.setColor(Color.RED);
        g2d.fill(new Polygon(xPos,yPos,3));

        if(redTriTimer > -10){
            redTriTimer -= 0.2;
        }else{
            redTriTimer = 10;
        }
    }

    public void movePanzer(){
        for(Player player : players){

            if(!shot) {

                player.move(this,map);
            }
            if(allLockedIn()){
                shoot();
            }
        }
    }

    public boolean allLockedIn(){
        for(Player player : currentPlayer){
            if(!player.isLockedIn()){
                return false;
            }
        }
        return true;
    }



    public int getHeight() {
        return height;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public void nextPlayer(){

       switch(spielmode){
           case 1:
               for(Player player : players){
                   if(player == currentPlayer.getFirst()){
                       currentPlayer.remove(player);

                       if(players.indexOf(player) == players.indexOf(players.getLast())){
                           currentPlayer.add(players.getFirst());
                           break;
                       }else{
                           currentPlayer.add(players.get(players.indexOf(player) + 1));
                           break;
                       }
                   }
               }
               break;
           case 2:
               if(currentPlayer.getFirst().getTeam() == teamanzahl){
                   currentPlayer = new LinkedList<>();
                   for(Player player : players){
                       if(player.getTeam() == 1){
                           currentPlayer.add(player);
                       }
                   }
               }else{
                   int t = currentPlayer.getFirst().getTeam();
                   for(Player player : players){
                       if(player.getTeam() == t+1){
                           currentPlayer.add(player);
                       }
                   }
               }

               break;
           case 3:
               break;
       }


    }

    public void removeWeapon(Weapon weapon){
        if(!sandbox) {
            currentWeapons.remove(weapon);
        }
    }

    public void changeGui(){

    }

    public void nextTurn(){

        if(currentWeapons.size() == 0) {

            for (Player player : players) {
                if (player.getPanzer().getLeben() <= 0) {
                    players.remove(player);
                }
            }
            if (!battleEnd()) {
                for (Player player : currentPlayer) {
                    player.setOnTurn(false);
                    player.setLockedIn(false);
                    player.getPanzer().resetSprit();

                }

                nextPlayer();

                for (Player player : currentPlayer) {
                    player.setOnTurn(true);

                }

                changeGui();

                for (Player player : currentPlayer) {
                    if (player.isLocalHuman()) {
                        lastLocalHuman = player;
                    } else if (player.isKi()) {
                        player.prepare(this);
                    }
                }

                shot = false;
            } else {
                endGame();
            }
        }
    }

    public boolean battleEnd(){
        int t = players.getFirst().getTeam();
        for(Player player : players){
            if(player.getTeam() != t){
                return false;
            }
        }

        return true;
    }

    public void endGame(){

    }

    public void handleMousePressed(int x,int y){
        if(!shot) {

            for(Player player : currentPlayer){
                if (player.isLocalHuman()) {
                    if (player.getPanzer().isSelected()) {


                        player.getPanzer().setTemps();

                        player.getPanzer().changeRohr(x,y,this);

                        player.getPanzer().setShootready(true);


                    }
                }
            }


        }
    }

    public void handleMouseClicked(int x,int y){
        if(!shot) {

            for(Player player : currentPlayer){
                if (player.isLocalHuman()) {
                    if (player.getPanzer().isSelected()) {

                        player.getPanzer().setTemps();

                        player.getPanzer().changeRohr(x,y,this);

                        player.getPanzer().setShootready(true);




                    }
                    if (player.getPanzer().isHit(x, y)) {
                        player.getPanzer().setSelected(true);
                    }

                }
            }

        }
    }

    public void drawUi(Graphics2D g2d,int x,int y) {
        if (currentPlayer != null) {
            if (!shot) {
                for (Player player : currentPlayer) {
                    if(player.isLocalHuman()) {
                        player.getPanzer().drawUi(g2d, x, y, this);
                    }
                }
            }
        }
    }


    public int getWeaponsShowedTime() {
        return weaponsShowedTime;
    }

    public boolean showWeapons(Graphics2D g2d, int mousex, int mousey, boolean clicked,int art){

        if(art == 1) {
            if (weaponsShowedTime <= GameLoop.imgH / 20) {
                g2d.fillRect(0, (int) (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, (int) 20 * weaponsShowedTime);
                weaponsShowedTime += 2;
            } else {
                g2d.fillRect(0, 0, GameLoop.imgW, GameLoop.imgH);

            }
        }else{
            if (weaponsShowedTime > 0) {
                g2d.fillRect(0, (int) (GameLoop.imgH - 20 * weaponsShowedTime), GameLoop.imgW, (int) 20 * weaponsShowedTime);
                weaponsShowedTime -= 4;
            }
        }

        int xtemp = GameLoop.imgW/5;
        int ytemp = GameLoop.imgH/8;

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
                        weapon.drawImage((int) (t % 5 * xtemp - xtemp * 0.1), (int) ((t / 5) * ytemp + height - h * ytemp - ytemp * 0.1), (int) (xtemp * 1.2), (int) (ytemp * 1.2), g2d);
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

        if(clicked && !handeld){
            MyKeys.weapon = false;
        }

        return clicked;


    }

    public void drawCurrentWeapons(Graphics2D g2d){
        for(Weapon weapon : currentWeapons){
            weapon.draw(g2d);
        }
    }

    public boolean isCollisionPanzer(int x, int y, Panzer herkunft){
        for(Player player : players){
            if(player.getPanzer() != herkunft) {
                if (player.getPanzer().isHit(x, y)) {
                    return true;
                }
            }

        }
        return map.isCollision(x,y);
    }

    public void shoot(){
        if(!shot) {
            if(isServer){
                sendWeaponsToAll();
            }

            for(Player player : currentPlayer) {

                player.shoot(this);


                currentWeapons.add(player.getSelectedWeapon());



                if(player.isLocalHuman() || player.isKi()) {
                    if(player.getSelectedWeapon().getAnzahl() == 1) {
                        player.getWeapons().remove(player.getSelectedWeapon());
                        player.setSelectedWeapon(player.getWeapons().getFirst());
                    }else{
                        player.getSelectedWeapon().subAnzahl();
                        player.getSelectedWeapon().reset();
                    }


                }

            }

            shot = true;
        }
    }

    private void sendWeaponsToAll() {
        for(Player playert : players){
            if(playert.getId() == 3){
                ((InetPlayer)playert).sendTCP("testWeapon");
            }
        }
        if(isServer && network){
            for(Player player : players){
                if(!player.isLocalHuman() && !player.isKi()){
                    for (Player player1 : players){
                        ((InetPlayer)player).sendTCP("weapon:" + player1.getId() +":"+ player1.getSelectedWeapon().getId());
                        ((InetPlayer)player).sendTCP("weaponvalues:" + player1.getId()
                                        + ":" + String.valueOf(player1.getPanzer().getBulletspawn().getX())
                                        + ":" + String.valueOf(player1.getPanzer().getBulletspawn().getY())
                                        + ":" + String.valueOf(player1.getPanzer().getRohrWinkel())
                                        + ":" + String.valueOf(player1.getPanzer().getShotstrength())
                                        + ":" + String.valueOf(player1.getPanzer().isOrientationRight()));
                    }
                }
            }
        }
    }

    public void feuerButtonAction(){
        for(Player player : currentPlayer) {
            if(player.isLocalHuman()) {
                player.setLockedIn(true);
                if(network && !isServer){
                    ((HumanPlayer)player).sendTCP("weapon:" + player.getId() +":"+ player.getSelectedWeapon().getId());
                    ((HumanPlayer)player).sendTCP("weaponvalues:" + player.getId()
                            + ":" + String.valueOf(player.getPanzer().getBulletspawn().getX())
                            + ":" + String.valueOf(player.getPanzer().getBulletspawn().getY())
                            + ":" + String.valueOf(player.getPanzer().getRohrWinkel())
                            + ":" + String.valueOf(player.getPanzer().getShotstrength())
                            + ":" + String.valueOf(player.getPanzer().isOrientationRight()));
                }
            }
        }

    }

    public void addWeapon(Weapon weapon){
        currentWeapons.add(weapon);
    }


    public void explosion(int x,int y,int size,int damage,Panzer herkunft){
        map.explosion(x,y,size,damage,herkunft);
    }



    public void movePanzerDown(int x,int y,int size,int damage,Panzer herkunft) {

        int team = 0;

        for(Player player : players){
            if(player.getPanzer() == herkunft){
                team = player.getTeam();
            }
        }


        for(Player player : players){

            player.getPanzer().moveNotTurn(map);
            player.getPanzer().setPolyPoints(this);

            if(new Ellipse2D.Double(x - size/(double)2,y -size/(double)2,size,size).intersects(player.getPanzer().getHitbox().getBounds2D())){



                if(player.getTeam() != team) {

                    player.getPanzer().schaden(damage, 0,sandbox);
                }else{

                    player.getPanzer().schaden(damage, 1,sandbox);
                }
            }

        }
    }

    public Player getGegener(Player playert){
        for(Player player : players){
            if(player.getTeam() != playert.getTeam()){
                return player;
            }
        }
        return null;
    }

    public LinkedList<Player> getPlayer() {
        return players;
    }

    public boolean isCollision(int x,int y){
        return map.isCollision(x,y);
    }

    public void sendToServer() {
        if(!isServer && network){
            for(Player player : players){
                if(player.isLocalHuman()){
                    ((HumanPlayer)player).sendToServer();
                }
            }
        }
    }

    public void sendToAll() {
        if(isServer && network){
            for(Player player : players){
                player.send(server);
            }
        }
    }

    public int getNextId(){
        highId++;
        return highId;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public Player getLastLocalHuman() {
        return lastLocalHuman;
    }
}
